# Fachpraktikum_K01589

Erstellung einer Erklärungskomponente für einen informationellen medizinischen Chatbot mittels Argumentationsbäumen im Fachpraktikum "Natural Language Processing, Information Extraction und Retrieval" der FernUniversität in Hagen
***

## Inhaltsübersicht


- [Fachpraktikum_K01589](#fachpraktikum_k01589)
  * [Beschreibung](#beschreibung)
    + [Verwendete Techniken:](#verwendete-techniken)
    + [Funktionsweise der Anwendung:](#funktionsweiße-der-anwendung)
      - [REST-API](#rest-api)


## Beschreibung

Bei der vorliegenden Anwendung handelt es sich um eine Java Spring Boot Anwendung, die einen Prototypen für eine Erklärungskomponente darstellt. Dabei werden Argumentationsbäume im AIF-Format eingelesen und verarbeitet. 


### Verwendete Techniken:

- Java 18
- Apache Jena Framework
- Spring Boot Framework
- Arguement Interchange Format 
- Ressource Description Framework


### Funktionsweise der Anwendung:



Unter dem Package *view* findet man die Klasse *MainShower*. Führt man die Klasse aus, so öffnet sich ein Dialog, der alle hinterlegten Argumentationsbäume anzeigt.


![image](https://user-images.githubusercontent.com/122731286/219345453-60dc101a-05d1-4fca-a222-b795fb7bba09.png)


Hier kann man einen Argumentationsbaum aus der Liste auswählen und sich anzeigen lassen.


![image](https://user-images.githubusercontent.com/122731286/219345739-dd7d4f5b-b963-4058-9477-09f121d89303.png)


Zusätzlich ist es möglich die ArgStrenght der einzelnen Prämissen anzupassen, falls bei den I-Nodes eine ArgStrenght hinterlegt ist. 


Wenn man auf den Button *Dialog starten* klickt, dann öffnet sich ein Dialog bei dem der Patient/User gefragt wird, ob die jeweilige Prämisse der Arguementationsbäumen eintreffen, um so auf eine Conclusion zu schließen und eine Diagnose zu erstellen.

#### REST-API

In der Spring Boot Anwendung wurde ein Microservice implementiert, worüber der Chatbot und die Erklärungskomponente per REST-Schnittstelle miteinander kommunizieren können. Dabei erfolgt der Datenaustausch über JSON. 


Mit dem Befehl *settree* kann man einen Baum auswählen:

http://localhost:8080/settree?tree=Argbaum1

Der Befehl *getpremises* zeigt die Prämissen des ausgewählten Baumes an:

http://localhost:8080/getpremises

Der Befehl *getconclusions* zeigt die Konklusion des ausgeählten Baumes an:

http://localhost:8080/getconclusions



