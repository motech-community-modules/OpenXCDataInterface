# OpenXCDataInterface
To expose custom APIs that will provide all vehicle data, a custom module named OpenXCDataInterface. This module is added to the MOTECH, above the core platform along with other custom modules, to make APIs available.

Purpose: First to use this custom module, the vehicle data (like speed, current latitude-longitude) need to be fetched which is received from OpenXC device via OBD2 port VI present in the vehicles. Fetched data can be saved in the custom tables of MOTECH database and then exposed through the web service APIs that will provide these data to consumer (developer) for further usage.”

For More Detail, please refere to below document: "docs/Developer Reference - OpenXC Backend OpenXCDataInterface.docx"
