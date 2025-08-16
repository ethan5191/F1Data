Project Status: Work in Progress (WIP)

Getting Started

Running from an IDE

    To launch the application, open the project in your IDE and run the Main.java file. This will initiate the application's UI thread and the separate data processing thread.

This project is in active development. While some telemetry data is still printed to the console for debugging, the primary focus has shifted to rendering data in dedicated UI components.

The data processing logic runs continuously in the background, regardless of a component's visibility. This allows for dynamic control of the UI, such as showing or hiding panels based on whether the car is on the track or in the garage.

Current Features

The application currently supports six separate data panels:

    Latest Lap: Displays the most recent lap time and details for all drivers on the track.

    All Laps: Provides a detailed breakdown of every lap for all drivers.

    Car Setup: Shows the current vehicle setup (e.g., wing settings, camber, toe) for all drivers.

    Fastest Speed Trap: Ranks the fastest speed trap times for each individual driver.

    All Speed Traps: Records and displays every speed trap time for both the player and their teammate.

    Run Data: Tracks and presents run-specific data for both the player and their teammate.

Architecture Overview

F1DataMain

This is the core class of the application. It is responsible for:

    Receiving and processing raw UDP telemetry data packets.

    Parsing and interpreting the received data.

    Building and managing the internal data structures that hold the state of the session.

F1DataUI

This class manages the user interface. It is responsible for:

    Handling the UI thread.

    Running the F1DataMain class on a separate, dedicated thread to ensure the UI remains responsive.

Panel Structure

Each UI panel follows a consistent architecture:

    Stage: Each panel is managed by its own Stage class, which extends the abstractStage parent.

    Dashboard: Each panel's visual content is a custom Dashboard class that extends the HBox object. 
Data Consumers

The application uses two primary data consumers to process specific telemetry data types:

    DriverDataDTO: Used by the lap time and car setup panels.

    SpeedTrapDTO: Used by both speed trap panels.

Compatibility

    The application has been tested with F1 2020 and F1 2024.
    In theory, it should work with all titles from F1 2020 through F1 2025, but support for 2021–2023 is unverified, and testing has not yet been performed with F1 2025.

Notes on F1 2020

Speed Trap Data

In F1 2020, speed trap data was only transmitted when a new fastest overall trap speed was set for the session.
To address this limitation, the application uses the distance of the first recorded trap and queries all drivers’ speeds within a ±1.75 unit threshold around that distance.

This can result in small discrepancies between in-game values and those shown in the panel.

    Example: The in-game HUD may show 314.8 kph, but if that was not the session’s fastest trap, the UDP stream would not include it. The panel might instead display 314 kph.

    In rare cases, if no LapData packet is received within the distance threshold, the trap speed may appear as 0.0.

Tyre Wear Data

Tyre wear values in 2020 were only transmitted as whole numbers.

    Example: 2.00, 3.00, etc.

    Decimal precision for tyre wear was introduced in F1 2021 and is supported in newer games.

Notes on F1 2020–2022

Tyre Set Identification

The tireSets packet was introduced in F1 2023.

    For earlier games (2020–2022), the app cannot uniquely identify tyre sets. Runs are grouped only by compound (Soft, Medium, Hard).

    This means multiple runs on the same compound with the same setup will appear in a single RunData section, even if a fresh tyre set was actually fitted.

    In F1 2023 and later, the app uses the fitted tyre index, allowing it to distinguish between different sets of the same compound.

Work is ongoing to improve handling for older games.


Future Projects

The following features and improvements are planned for upcoming releases:

UI Improvements

    Refined Data Layout: The existing UI will be updated to ensure data is more logically organized and easier to read.

    Dynamic Data Display: Functionality will be added to allow users to show or hide specific data points on select panels (e.g., toggling sector times on the Latest Lap panel or energy information on the Run Data panel).

    Scroll Panel Fixes: The scrolling functionality for both ScrollPane components is currently non-functional and will be repaired.

Core Application Enhancements

    Logging Integration: Implement a robust logging system to replace the current console-based output.

    Code Cleanup: All remaining print statements will be removed from the codebase.

    Packaging: The project will be configured to produce a distributable artifact (e.g., a .jar file) for easy deployment.