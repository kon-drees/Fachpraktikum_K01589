# Fachpraktikum_K01589

Erstellung einer Erklärungskomponente für einen informationellen medizinischen Chatbot mittels Argumentationsbäumen im Fachpraktikum "Natural Language Processing, Information Extraction und Retrieval" der FernUniversität in Hagen
***

## Inhaltsübersicht


- [Fachpraktikum_K01589](#fachpraktikum_k01589)
  * [Beschreibung](#Beschreibung)
    + [Verwendete Techniken:](#verwendete-techniken)
    + [Funktionsweise der Anwendung:](#Funktionsweise-der-anwendung)
      - [REST-API](#rest-api)


## Beschreibung

Bei der vorliegenden Anwendung handelt es sich um eine Java Spring Boot Anwendung, die einen Prototypen für eine Erklärungskomponente darstellt. Dabei werden Argumentationsbäume im AIF-Format eingelesen und verarbeitet. 

Darüber hinaus wird eine grafische Oberfläche angeboten, um Auswirkungen der Argumentationsbäume testen zu können und ggf. Anpassungen an einem Attribut vorzunehmen. 

Ferner biete die Anwendung einen Aufruf über die Kommandozeile.

### Verwendete Techniken:

- Java 18
- Apache Jena Framework
- Spring Boot Framework
- Argument Interchange Format 
- Ressource Description Framework
- JavaFX
- Representational State Transfer


### Funktionsweise der Anwendung:


Unter dem Package *view* findet man die Klasse *MainShower*. Führt man die Klasse aus, so öffnet sich ein Dialog, der alle hinterlegten Argumentationsbäume anzeigt.


![image](https://user-images.githubusercontent.com/122731286/219345453-60dc101a-05d1-4fca-a222-b795fb7bba09.png)


Hier kann man einen Argumentationsbaum aus der Liste auswählen und sich anzeigen lassen.


![image](https://user-images.githubusercontent.com/122731286/219345739-dd7d4f5b-b963-4058-9477-09f121d89303.png)


Zusätzlich ist es möglich die ArgStrength, durch Mausklick auf die einzelnen Prämissen anzupassen, falls der Baum mindestens zwei RA-Nodes über einen CA-Node verbunden hat. Nur dann kommt es im Dialog zur Auswertung dieser Eigenschaft. 


Wenn man auf den Button *Dialog starten* klickt, dann öffnet sich ein Dialog, bei dem der User gefragt wird, ob die jeweilige Prämisse der Arguementationsbäumen eintreffen, um so auf eine Conclusion zu schließen und eine Diagnose zu erstellen.

#### REST-API

In der Spring Boot Anwendung wurden Microservices implementiert, worüber z.B. ein möglicher Chatbot auf die Erklärungskomponente per REST-Schnittstelle miteinander kommunizieren können. Dabei erfolgt der Datenaustausch über JSON. 

Die Spring Boot Applikation wird über die Klasse SpringBootStarter im package fp1589 gestartet. Dabei wird eine Tomcat-Instanz unter dem Port 8080 gestartet.


Mit dem Befehl *settree* muss zuerst ein Baum auswählt werden:

http://localhost:8080/settree?tree=Argbaum1

Der Befehl *getpremises* gibt die Prämissen des ausgewählten Baumes im Json-Format zurück.

http://localhost:8080/getpremises

Der Befehl *getconclusions* zeigt die Konklusion des ausgewählten Baumes an. Dabei ist dem Parameter inodes per PUT-Request im JSON-Format die Premises zu übergeben, die erfüllt sind. Das Format kann dem Rückgabeergebnis der Methode "getpremises" entnommen werden.

http://localhost:8080/getconclusions

Getestet werden können diese drei Mathoden per curl wie folgt:


```curl
curl localhost:8080/settree?tree=Argbaumx  (Nummer des Argbaums festlegen) 
```

```curl
curl localhost:8080/getpremises
```


Das Ergebnis dieses Aufrufs kann anschließend in eine txt-Datei gespeichert werden, z.B. t1.txt

Die Premises, die nicht erfüllt, sofern vorhanden, sind in der Datei zu entfernen.

Abschließend bekommt man die conclusion(s) mittels

```curl
curl localhost:8080/getconclusions -X PUT -d @t1.txt -H "Content-Type: application/json"
```


#### Kommandozeile

Die Klasse FPController kann mit Angabe des zu verwendenen Argumentationsbaums aufgerufen werden (Argbaum1 - 9). Ohne Angabe wird der Argbaum1 verwendet. Es erfolgt der Dialog via Kommandozeile. Hierbei werden die premises nach erfüllt oder nicht erüllt abgefragt. Ferner kann über die Eingabe "warum" der Grund für die Abfrage des jeweiligen Premises erfragt werden.
