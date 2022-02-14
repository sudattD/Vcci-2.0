package sbnri.consumer.android.places

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Address
import android.location.Geocoder
import androidx.annotation.IntDef
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger
import sbnri.consumer.android.data.local.SBNRIPref

import java.io.IOException
import java.util.*

class SearchAndAutoDetectionHelper private constructor(
        private val mListener: SearchAndAutoDetectionListener
) {

    //Google
    private var mPlaceDetectionClient: PlacesClient? = null
    private var geoCoder: Geocoder? = null
    private var token: AutocompleteSessionToken? = null

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(AUTODETECT, SEARCH_PLACES, GET_CURRENT_LOCATION)
    private annotation class CurrentLocationLoadReason

    @SuppressLint("SwitchIntDef")
    fun displayLocationSettingsRequest() {
        displayLocationSettingsRequestGoogle()
    }

    private fun displayLocationSettingsRequestGoogle() {
        if (mListener.context == null)
            return
        val builder = LocationSettingsRequest.Builder().addLocationRequest(mListener.locationRequest)
        val client = LocationServices.getSettingsClient(mListener.context!!)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { mListener.startLocationUpdates() }
        if (mListener.context is Activity)
            task.addOnFailureListener(mListener.context as Activity) { e ->
                if (e is ApiException)
                    when (e.statusCode) {
                        CommonStatusCodes.RESOLUTION_REQUIRED ->
                            // TestCenterLocation settings are not satisfied, but this can be fixed
                            // by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                val resolvable = e as ResolvableApiException
                                resolvable.startResolutionForResult(mListener.context as Activity, REQUEST_CHECK_SETTINGS)
                            } catch (sendEx: IntentSender.SendIntentException) {
                                // Ignore the error.
                            }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->
                            // TestCenterLocation settings are not satisfied. However, we have no way
                            // to fix the settings so we won't show the dialog.
                            mListener.onUnableToStartGps()
                    }
            }
    }



    private fun initPlacesClient() {
        if (mPlaceDetectionClient == null && mListener.context != null)
            mPlaceDetectionClient = Places.createClient(mListener.context!!)
    }



    private fun reverseGeoCodeLocation(latitude: Double, longitude: Double): List<Address>? {
        val addressLineInt = 1
        val addresses = catchAddress(addressLineInt, latitude, longitude)
        if (addresses != null) {
            if (addresses.isEmpty()) {
                mListener.hideProgress()
                mListener.showToastMessage("Unable to track", true)
            }
        } else {
            mListener.hideProgress()
        }
        return addresses
    }


    private fun catchAddress(addressLineInt: Int, latitude: Double, longitude: Double): List<Address>? {
        var addressLineInteger = addressLineInt
        try {
            if (mListener.context != null) {
                if (geoCoder == null)
                    geoCoder = Geocoder(mListener.context, Locale.getDefault())
                return geoCoder!!.getFromLocation(latitude, longitude, addressLineInt)
            }
        } catch (e: IOException) {
            addressLineInteger++
            if (addressLineInteger < 12) {
                return catchAddress(addressLineInteger, latitude, longitude)
            }
        }
        return null
    }

    fun getAddressForAutoCompletePlace(place: AutoCompletePlace) {

        getAddressFromPlace(place.id)
    }

    private fun getAddressFromPlace(id: String) {
        val request = FetchPlaceRequest.builder(id, PLACE_FIELDS).build()
        mPlaceDetectionClient?.fetchPlace(request)?.addOnCompleteListener {
            when (it.isSuccessful && it.result != null) {
                true -> {
                    val place = it.result?.place
                    val addresses = reverseGeoCodeLocation(place?.latLng!!.latitude, place.latLng!!.longitude)
                    if (addresses?.isNotEmpty() == true)
                        mListener.onAddressRetrievedById(addresses[0])
                }
            }
        }
    }

    fun getAutocomplete(constraint: CharSequence?) {
        getAutocompletePrediction(constraint)

    }

    private fun getAutocompletePrediction(constraint: CharSequence?) {
        initPlacesClient()
        if (token == null)
            token = AutocompleteSessionToken.newInstance()
        val request = FindAutocompletePredictionsRequest.builder()
                .setCountry("IN")
                .setSessionToken(token)
                .setQuery(constraint?.toString())
                .build()
        var response = mPlaceDetectionClient?.findAutocompletePredictions(request)?.addOnCompleteListener {
                    if (it.isSuccessful && it.result != null && it.result!!.autocompletePredictions.isNotEmpty()) {
                        mListener.onAutoCompletePlacesFetched(
                                it.result!!.autocompletePredictions.map { autocompletePrediction ->
                                    AutoCompletePlace(autocompletePrediction.getFullText(null).toString(), autocompletePrediction.placeId)
                                }.toMutableList()
                        )
                    } else {
                        mListener.showToastMessage("",true)
                    }
                }
    }

    interface SearchAndAutoDetectionListener {

        val locationRequest: LocationRequest

        val context: Context?

        var requestingCurrentLocation: Boolean

        val sbnriPref: SBNRIPref

        fun onUnableToStartGps()

        fun startLocationUpdates()

        fun onLocationAutoDetected(latitude: Double, longitude: Double, addresses: List<Address>?, pincode: String?)

        fun couldNotAutoDetectLocation()

        fun onPermissionDeniedForever()

        fun showProgress(string: String)

        fun showToastMessage(string: String, isError: Boolean)

        fun hideProgress()


        fun onAutoCompletePlacesFetched(places: MutableList<AutoCompletePlace>)

        fun onAddressRetrievedById(addresses: Address)
    }

    companion object {

        const val REQUEST_CHECK_SETTINGS = 1234
        val RE_LOAD_CURRENT_LOCATION_MIN_WAIT = (30 * 60 * 60 * 1000).toLong() // half an hour

        private const val AUTODETECT = 1
        private const val SEARCH_PLACES = 2
        private const val GET_CURRENT_LOCATION = 3
        private val PLACE_FIELDS = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)

        fun newInstance( listener: SearchAndAutoDetectionListener): SearchAndAutoDetectionHelper {
            return SearchAndAutoDetectionHelper( listener)
        }
    }

}
