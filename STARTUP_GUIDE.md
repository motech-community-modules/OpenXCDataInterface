

####STARTUP GUIDE for mHealth App, mHealthDataInterface and OpenXCDataInterface


#####Overview

Purpose of this document is to provide the step wise approach to setup MOTECH Platform Server, modules (mHealthDataInterface, OpenXCDataInterface) and [mHealthApp][]. Modules ([mHealthDataInterface][], [OpenXCDataInterface][]) are deployed and run on MOTECH Platform Server. Application mHealthApp is based on Android platform, it shows the patient and health worker related information and health worker vehicle location tracking on run-time. mHealthApp uses the services exposed by the MOTECH modules ([mHealthDataInterface][], [OpenXCDataInterface][]).

#####Steps to setup the complete solution :

  1. MOTECH Platform Server Setup – 
  
    This is the very first step. Developers who want to make development on top of MOTECH platform, first need to set up the whole development environment for MOTECH. 
    For setting up the MOTECH Platform, use the instructions mentioned in [Motech_Platform_Server_Setup_Guide.docx][].
  
  2. [mHealthDataInterface][] Module Installation – 
  
    Purpose of this module is to fetch detail of patients and health workers from MOTECH and expose required web service APIs providing these data to consumers for further usage.
    In this step we deploy the mHealthDataInterface Module which exposes Restful webservices APIs. 
    For more detail for installing the mHealthDataInterface Module in [mHealthDataInterface Developer Reference Document][].
 
  3. [OpenXCDataInterface][] Module Installation – 
  
    Purpose of this module is to use MOTECH custom module that can be saved fetched vehicle data (like speed, current latitude-longitude received from OpenXC device via OBD2 port VI present in the vehicles) in to the custom tables of MOTECH database via web services APIs and also provide these data to consumer for further usage.
    For more detail for installing the OpenXCDataInterface Module in [OpenXCDataInterface Developer Reference Document][].
  
  4. [mHealthApp][] Setup – 
  
    This is an Android Application that implement the usecases to consume the webservices APIs exposed by [mHealthDataInterface][], [OpenXCDataInterface][] moduels.
    
    For more detail about features of mHealth App, follow [Feature Set][] Document.
    
    For Installable Binary APK and Source code please refer to [mHealthApp][]. 

[Motech_Platform_Server_Setup_Guide.docx]:../../../mHealthDataInterface/blob/master/docs/Motech_Platform_Server_Setup_Guide.docx
[mHealthDataInterface]:../../../mHealthDataInterface
[OpenXCDataInterface]:../../../OpenXCDataInterface
[mHealthApp]:../../../mHealth-App
[mHealthDataInterface Developer Reference Document]:../../../mHealthDataInterface/blob/master/docs/MOTECH_mHealthDataInterface_Developer_Reference_v2.docx
[OpenXCDataInterface Developer Reference Document]:../../../OpenXCDataInterface/blob/master/docs/MOTECH_OpenXCDataInterface_Developer%20Reference.docx
[Feature Set]:../../../mHealth-App/blob/master/docs/mHealth_Feature_Set.docx
