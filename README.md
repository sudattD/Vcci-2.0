# SBNRI-Android
SBNRI-Android codebase


Android code-base with MVP architecture and using Dagger-2 dependency injection .
extend BaseActivity when you create a new activity - 
extend BaseFragment when you create a new Fragment.
For Fragment transactions use FragmentUtils methods - add , Replace etc.
Extend BaseFragmentActivity when you have a combination of Activity and fragments.

create a Contract interface for each component you create and if you wish to bind other injected components 
implement this overridden method - callDependencyInjector . and to give it a view implement getBaseView().

presenters will have all the logics including network calls and generating params .
common logics can be a part of some of the UTIL classes.
wherever required make use of KotlinExtension functions . --- > KotlinExtensions.kt File class.

Happy Coding !!!!
