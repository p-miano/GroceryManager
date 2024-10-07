# Grocery Manager

Grocery Manager is an Android application designed to help users manage their grocery items with ease. Users can add, update, and delete grocery items, categorized into various groups such as Fruits, Dairy, Vegetables, Meat, and Beverages. The app uses Firebase Authentication for user management and SQLite for local data storage.

## Features
- **User Authentication:** Log in and sign up functionality using Firebase Authentication.
- **Grocery Management:** Add, update, and delete grocery items specific to each logged-in user.
- **Category Support:** Categorize grocery items into predefined categories.
- **MVVM Architecture:** Utilizes Model-View-ViewModel (MVVM) for a scalable and maintainable codebase.
- **Persistent Storage:** Local data stored using SQLite for offline access.

## Screenshots
![image](https://github.com/user-attachments/assets/aaee99c5-2cac-40cb-9491-a106b5231363)
![image](https://github.com/user-attachments/assets/19029473-6cdf-4d0d-8173-1821751ec95c)

## Technologies Used
- **Android SDK**
- **Java**
- **SQLite** for local database management
- **Firebase Authentication** for user management
- **MVVM Architecture** for separation of concerns
- **ConstraintLayout and RecyclerView** for a responsive and interactive UI

## Getting Started

### Prerequisites
- Android Studio installed
- Firebase account for setting up Firebase Authentication
- Basic knowledge of Android development and Java

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/grocery-manager.git
   ```
2. Open the project in Android Studio.

3. Set up Firebase Authentication:
   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Create a new project and add an Android app.
   - Download the `google-services.json` file and place it in the `app/` directory.
   - Make sure to enable Email/Password sign-in in the Firebase Authentication settings.

4. Build and run the project on an emulator or physical device.

### Database Migration
If you encounter issues related to the database (e.g., changes in structure), uninstall the app from your emulator/device and reinstall it. This will reset the database with the latest schema.

## Usage
- Log in or sign up using your email and password.
- Add grocery items with names and categories.
- Update or delete items as needed. Each user's data is isolated based on their login.

## Project Structure
The project follows the MVVM architecture and is organized as follows:
- **data/**: Contains database-related classes like `GroceryDatabaseHelper` and data models.
- **repository/**: Handles the data operations and communicates with the ViewModel.
- **ui/**: Contains all the UI components such as activities and adapters.
- **viewmodel/**: ViewModel classes that hold the logic and interact with the repository.

## Contributing
Contributions are welcome! If you'd like to improve this project, feel free to fork the repository and submit a pull request.

1. Fork the repository.
2. Create a new branch for your feature:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

## Educational License

This project is developed as part of a course at College LaSalle Montreal. It is intended for educational purposes only. 
The code may not be used, copied, modified, or distributed for commercial purposes or outside of the course without explicit permission from the author.
