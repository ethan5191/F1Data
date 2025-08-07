Project Status: Work in Progress (WIP)

This project is currently in an early development stage. At the moment, it prints telemetry data directly to the console of an IDE. Logging functionality has not yet been implemented, and the project is not yet packaged (e.g., no .jar or .war build available).
Current Features

    Lap Completion Output
    When any driver completes a lap, the project prints detailed lap information. This data is sourced from the IndividualLapInfo object and uses its built-in print methods.

    Pause-Based Data Output
    Setup and lap time data are printed when the pause button is pressed.

        Two pause button values are currently supported.

        Both values are mapped specifically for the McLaren GT3 Fanatec wheel.

        Future plans include:

            Making the pause button mapping configurable.

            Possibly removing this trigger once UI components are implemented.

    F1 24 UDP Telemetry Support
    Constants exist for all F1 24 UDP telemetry packet types, though the project currently only processes a subset of them.

Architecture Overview

    F1DataMain
    This is the main class responsible for:

        Receiving UDP telemetry data.

        Parsing the data packets.

        Building and managing the internal data structures.