# Architecture of the index builder

## The scope
The scope of this project is to build an index builder for the files located on the local disk of the user. This file would contain the description of the overall architecture of the application.

## The C4 model 
The C4 model describes the architecture similar to how an online map would describe an area: zooming in and zooming out. In this context the C4 architecture confers 4 levels of description for the software system: 
*   System Contex: describes the environment of the system and the components with which the system interacts;
*   Container level: describes the main high-level technologies and shape of the software system and how reponsabilities are distributed across it;
*   Component level: describes how the roles of a container are distributed acress its components and how they comunicate with each other;
*   Classes or code level: describes the design of each component, the separation in classes and interfaces.

### The system contex
![The system context diagram](.\System_Context_Diagram.png) 
Our system interacts with the local user of the computer and the file system of the computer.
    