# TheSportDB
Application for technical test FDJ

This mini application is built following an MVVM Clean architecture. Three seperate layers are set in the project (presentation, domain and data). The application uses latest Kotlin version v1.8.20

# Libraries used
- Android Hilt : For dependency injection
- Kotlin Coroutines, Koltin Flow : Handle asynchronous stream of data
- Retrofit2 : Handle api calls
- Room DB : Local Database
- Coil : For fetching images
- AndroidX Security : Use encrypted shared preferences
- Ktlint : Code formating
- Mockk : Mocking in Koltin

# Why Add Local Database ?
The decision of fetching the data from api or local database is made by the class `DataSourceDecisionMaker`.

We went with this decision for 2 reasons :
- If Internet is not available, we want the user to still be able to use the application.
- Since the type of data that we're fetching from api does not get updated or changed very often, we want to minimize the api calls and use the data we save inside our local databse instead.