package com.example.youtubeplayertest.di

import com.example.youtubeplayertest.MainActivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * 用於設置UI ViewModel的Module, 初始化在BaseApplication的getKoinModules()
 */
////測試API用
//val apiResponseModuleScope = module {
//    fun provideApiResponseModel(repository: Repository): ApiResponseModel =
//        ApiResponseModel.getInstance(repository)
//    single { provideApiResponseModel(get()) }
//    factory { ApiResponseViewModel.getInstance(androidContext(), get()) }
//}

val mainActivityViewModuleScope = module {
    factory { MainActivityViewModel.getInstance() }
}

////登入ViewModel
//val loginViewModuleScope = module {
//    fun provideLoginModel(repository: Repository): LoginModel = LoginModel.getInstance(repository)
//    single { provideLoginModel(get()) }
//    factory { LoginViewModel.getInstance(androidContext(), get()) }
//}
//
////首頁ViewModel
//val homeViewModuleScope = module {
//    fun provideHomeModel(repository: Repository): HomeModel = HomeModel.getInstance(repository)
//    single { provideHomeModel(get()) }
//    factory { HomeViewModel.getInstance(androidContext(), get()) }
//}
//
////首頁近期賽事Fragment的ViewModel
//val homeRecentViewModuleScope = module {
//    fun provideHomeRecentModel(repository: Repository): HomeRecentMatchModel =
//        HomeRecentMatchModel.getInstance(repository)
//    single { provideHomeRecentModel(get()) }
//    factory { HomeRecentMatchViewModel.getInstance(androidContext(), get()) }
//}
//
////首頁近期賽事詳細DialogFragment的ViewModel
//val homeRecentMatchDetailViewModuleScope = module {
//    fun provideHomeRecentMatchDetailModel(repository: Repository): HomeRecentMatchDetailModel =
//        HomeRecentMatchDetailModel.getInstance(repository)
//    single { provideHomeRecentMatchDetailModel(get()) }
//    factory { HomeRecentMatchDetailViewModel.getInstance(androidContext(), get()) }
//}
//
////比分頁外層Fragment的ViewModel
//val matchListViewModuleScope = module {
//    fun provideMatchListModel(repository: Repository): MatchListModel =
//        MatchListModel.getInstance(repository)
//    single { provideMatchListModel(get()) }
//    factory { MatchListViewModel.getInstance(androidContext(), get()) }
//}
//
////比分頁ViewPager裡 賽事列表Fragment的ViewModel
//val matchListVpViewModuleScope = module {
//    fun provideMatchListVpModel(repository: Repository): MatchListVpModel =
//        MatchListVpModel.getInstance(repository)
//    single { provideMatchListVpModel(get()) }
//    factory { MatchListVpViewModel.getInstance(androidContext(), get()) }
//}
//
////比分篩選頁的ViewModel
//val filterViewModuleScope = module {
//    fun provideFilterModel(repository: Repository): FilterModel =
//        FilterModel.getInstance(repository)
//    single { provideFilterModel(get()) }
//    factory { FilterViewModel.getInstance(androidContext(), get()) }
//}
//
////賽事焦點頁的ViewModel
//val spotlightViewModuleScope = module {
//    fun provideSpotlightModel(repository: Repository): SpotlightModel =
//        SpotlightModel.getInstance(repository)
//    single { provideSpotlightModel(get()) }
//    factory { SpotlightViewModel.getInstance(androidContext(), get()) }
//}
//
////會員頁的ViewModel
//val memberViewModuleScope = module {
//    fun provideMemberModel(repository: Repository): MemberModel =
//        MemberModel.getInstance(repository)
//    single { provideMemberModel(get()) }
//    factory { MemberViewModel.getInstance(androidContext(), get()) }
//}
//
////足球資料庫的ViewModel
//val soccerDatabaseViewModuleScope = module {
//    fun provideSoccerDatabaseModel(repository: Repository): SoccerDatabaseModel =
//        SoccerDatabaseModel.getInstance(repository)
//    single { provideSoccerDatabaseModel(get()) }
//    factory { SoccerDatabaseViewModel.getInstance(androidContext(), get()) }
//}
//
////足球資料庫的 Detail ViewModel
//val soccerDatabaseDetailViewModuleScope = module {
//    fun provideSoccerDatabaseDetailModel(repository: Repository): SoccerDatabaseDetailModel =
//        SoccerDatabaseDetailModel.getInstance(repository)
//    single { provideSoccerDatabaseDetailModel(get()) }
//    factory { SoccerDatabaseDetailViewModel.getInstance(androidContext(), get()) }
//}
//
////賽事詳細頁的ViewModel
//val matchDetailViewModuleScope = module {
//    fun provideMatchDetailModel(repository: Repository): MatchDetailModel =
//        MatchDetailModel.getInstance(repository)
//    single { provideMatchDetailModel(get()) }
//    factory { MatchDetailViewModel.getInstance(androidContext(), get()) }
//}
//
////搜尋頁的ViewModel
//val searchViewModuleScope = module {
//    fun provideSearchModel(repository: Repository): SearchModel =
//        SearchModel.getInstance(repository)
//    single { provideSearchModel(get()) }
//    factory { SearchViewModel.getInstance(androidContext(), get()) }
//}
//
////新聞頁的ViewModel
//val newsModuleScope = module {
//    fun provideNewsModel(repository: Repository): NewsModel =
//        NewsModel.getInstance(repository)
//    single { provideNewsModel(get()) }
//    factory { NewsViewModel.getInstance(androidContext(), get()) }
//}