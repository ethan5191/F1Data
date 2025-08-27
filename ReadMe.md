## Table of Contents

- [Getting Started](#getting-started)  
  - [Java Version](#java-version)
  - [Telemetry Setup](#telemetry-setup) 
  - [Running from an IDE](#running-from-an-ide)
  - [Building and Running](#building-and-running-the-application)
- [Current Features](#current-features)  
- [Architecture Overview](#architecture-overview)  
  - [F1 Data Main](#f1datamain)  
  - [F1 Data UI](#f1dataui)
  - [Panel Structure](#panel-structure)
  - [Data Consumers](#data-consumers)
- [Compatibility Matrix](#game-compatibility-matrix)
- [Game Specific Notes](#game-specific-notes)
  - [F1 2020](#f1-2020)
  - [F1 2020-2022](#f1-20202022)
  - [F1 2024](#f1-2024)
- [Future Projects](#future-projects)
  - [UI Improvements](#ui-improvements)
  - [Enhancements](#core-application-enhancements)

___
# Project Status: Work in Progress (WIP)

## Getting Started

### Java Version
Requires Java 17 or later to run the application.

### Telemetry Setup
Make sure the game is configured to send telemetry data:

    Go to Settings → Telemetry Settings and set UDP Telemetry to ON.
    The app currently only supports the default port (20777).
    UDP Send Rate controls how many packets are sent per second (10 = lowest rate, 60 = highest). The application should work with any value, regardless of the game year.

### Running from an IDE

    To launch the application, open the project in your IDE and run the Main.java file. This will initiate the application's UI thread and the separate data processing thread.

### Building and Running the Application

To build a standalone, executable version of this application, use the Maven command line tool.

    Open your terminal or command prompt and navigate to the project's root directory.
    Run the following command to build the .jar file:
         mvn clean package
    Once the build is complete, you will find the executable .jar file in the target/ directory.

You can then run the application from the command line using the following command:
  
    java -jar your-app-name.jar

This project is in active development. While some telemetry data is still printed to the console for debugging, the primary focus has shifted to rendering data in dedicated UI components.

The data processing logic runs continuously in the background, regardless of a component's visibility. This allows for dynamic control of the UI, such as showing or hiding panels based on whether the car is on the track or in the garage.  
[Back to top](#table-of-contents)
___

## Current Features

The application currently supports six separate data panels:

    Latest Lap: Displays the most recent lap time and details for all drivers on the track.
    All Laps: Provides a detailed breakdown of every lap for all drivers.
    Car Setup: Shows the current vehicle setup (e.g., wing settings, camber, toe) for all drivers.
    Fastest Speed Trap: Ranks the fastest speed trap times for each individual driver.
    All Speed Traps: Records and displays every speed trap time for both the player and their teammate.
    Run Data: Tracks and presents run-specific data for both the player and their teammate.
[Back to top](#table-of-contents)
___

## Architecture Overview
### F1DataMain
This is the core class of the application. It is responsible for:  

    Receiving and processing raw UDP telemetry data packets.
    Parsing and interpreting the received data.
    Building and managing the internal data structures that hold the state of the session.

### F1DataUI
This class manages the user interface. It is responsible for:

    Handling the UI thread.
    Running the F1DataMain class on a separate, dedicated thread to ensure the UI remains responsive.

### Panel Structure
Each UI panel follows a consistent architecture:

    Stage: Each panel is managed by its own Stage class, which extends the abstractStage parent.
    Dashboard: Each panel's visual content is a custom Dashboard class that extends the HBox object. 

### Data Consumers
The application uses two primary data consumers to process specific telemetry data types:

    DriverDataDTO: Used by the lap time and car setup panels.
    SpeedTrapDTO: Used by both speed trap panels.
[Back to top](#table-of-contents)
___

## Game Compatibility Matrix
| Game Version | Tested                               | Speed Trap Data                        | Tyre Wear Precision | Tyre Set Identification        | Notes                                            |
|--------------|--------------------------------------|----------------------------------------|---------------------|--------------------------------|--------------------------------------------------|
| **F1 2020**  | ✔                                    | Limited (only when new fastest is set) | Whole numbers only  | Compound only (Soft/Med/Hard)  | Workarounds implemented for traps & wear         |
| **F1 2021**  | ✖ (untested)                         | Expected same as 2020                  | Whole numbers only  | Compound only                  | Untested, but should function                    |
| **F1 2022**  | ✔ (tested in 2024 using 2022 format) | Expected same as 2020/2021             | Whole numbers only  | Compound only                  | Tested in F1 2024 using the 2022 packet format   |
| **F1 2023**  | ✔ (tested in 2024 using 2023 format) | Full                                   | Whole numbers only  | Full tyre set via fitted index | Tested in F1 2024 using the 2023 packet format   |
| **F1 2024**  | ✔                                    | Full                                   | With decimals       | Full tyre set via fitted index | Development baseline, known accelerated-time bug |
| **F1 2025**  | ✖ (not tested)                       | Expected full                          | With decimals       | Full tyre set via fitted index | Pending release/testing                          |
  
[Back to top](#table-of-contents)
___

## Game-Specific Notes

    The application has been tested with F1 2020 and F1 2024.
    In theory, it should work with all titles from F1 2020 through F1 2025, but F1 2021–2023 have not been fully verified, and F1 2025 has not been tested.
    F1 2022 and 2023 have both been tested in F1 2024 using the specific years packet format. No issues have been found with either packet format in F1 '24.

### F1 2020

Speed Trap Data

    In F1 2020, speed trap data was only sent via UDP when a new session fastest trap was set.
    The app works around this by taking the first speed trap distance and sampling each car’s speed near that location (±1.75 distance).
    Because of this, small discrepancies may appear (e.g., the game shows 314.8 but the panel shows 314).
    Rarely, if no LapData packet is received within the threshold, a trap speed may show as 0.0.

Tyre Wear Precision

    Tyre wear was only transmitted as whole numbers (e.g., 2.00, 3.00). Decimal precision was added in later games.

### F1 2020–2022

Tyre Set Identification

The tyreSets packet did not exist until F1 2023.

    In F1 2020–2022, the app can only identify tyres by compound (Soft, Medium, Hard).
    Multiple runs on the same compound and setup will appear under the same RunData section.
    From F1 2023 onwards, the fitted index allows accurate tyre set tracking even within the same compound.

Work is in progress to improve handling for pre-2023 games.

### F1 2024

Development Baseline
    
    The app was originally built on the F1 2024 UDP spec, then updated for compatibility with earlier versions.
    Many comments and references are 2024-specific as a result.

Practice Session "Accelerated Time" Issue

    Using accelerated time in practice can cause corrupted values in the RunData panel:
        Negative tyre wear percentages.
        Store value of 4000000.00.
        Harvested and deployed values stuck at 0.0.
    This occurs when a lap ends while the game is running in accelerated time.
    Workaround: Switch back to normal speed before the car of interest completes its lap.
[Back to top](#table-of-contents)
___

## Future Projects

The following features and improvements are planned for upcoming releases:

### UI Improvements

    Refined Data Layout: The existing UI will be updated to ensure data is more logically organized and easier to read.
    Dynamic Data Display: Functionality will be added to allow users to show or hide specific data points on select panels (e.g., toggling sector times on the Latest Lap panel or energy information on the Run Data panel).

### Core Application Enhancements

    Code Cleanup: All remaining print statements will be removed from the codebase.
    Package the application so it can be started by double-clicking the JAR file, instead of requiring the command line.
    Possibly move towards a standalone installer, though not certain at this time.
    Save session data to enable reviewing information after the session ends.
    Generate graphs from run data to better visualize performance in a race-style format.

[Back to top](#table-of-contents)