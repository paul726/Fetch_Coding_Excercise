# Android Grouped List Demo

## Project Overview

This Android demo project showcases how to implement a RecyclerView with grouped items and expand/collapse functionality. The project utilizes modern Android development technologies and best practices, including MVVM architecture, coroutines, and dependency injection.

## Technology Stack

### Architecture
- **MVVM Architecture Pattern**: Using ViewModel to separate UI logic from UI controllers
- **Repository Pattern**: Data layer abstraction providing a clean API for the ViewModel

### Core Libraries
- **Kotlin**: 100% Kotlin codebase with coroutines and extension functions
- **Kotlin Coroutines**: For asynchronous programming and background tasks
- **Kotlin Flow**: For reactive data streams
- **AndroidX**: Latest Jetpack component libraries

### UI
- **Material Design Components**: Modern UI implementation
- **RecyclerView**: Efficient list display with DiffUtil for optimized updates
- **ViewBinding**: Type-safe view binding

### Network & Data
- **Retrofit2**: Type-safe HTTP client
- **OkHttp3**: Efficient HTTP client with interceptors and caching
- **Gson**: JSON parsing library

### Dependency Injection
- **Koin**: Lightweight dependency injection framework
    - Using new API `singleOf()` and `viewModel()` for simplified injection
    - Interface binding for loose coupling


## Key Features

- Display items grouped by `listId`
- Double sorting: first by `listId`, then by `name` within each group
- Filter out items where `name` is blank or null
- Groups support expand/collapse with efficient updates using DiffUtil
- Asynchronous data loading and updates using coroutines

## Main Activity Implementation

The main activity directly hosts the RecyclerView without using fragments:

## RecyclerView Optimization

- Utilizing `DiffUtil` for efficient list updates
- Custom grouping logic with expand/collapse functionality
- Optimized ViewHolder recycling for better performance

## Project Setup

### Prerequisites
- Android Studio Ladybug Feature Drop | 2024.2.2

### Build and Run
1. Clone the repository
2. Open the project in Android Studio
3. Wait for Gradle sync to complete
4. Run the project on an emulator or physical device