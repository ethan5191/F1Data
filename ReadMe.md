Project Status: Work in Progress (WIP)

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