# Fachpraktikum_K01589

Erstellung einer Erklärungskomponente für einen informationellen medizinischen Chatbot mittels Argumentationsbäumen im Fachpraktikum "Natural Language Processing, Information Extraction und Retrieval" der FernUniversität in Hagen
***

## Inhaltsübersicht


- [Fachpraktikum_K01589](#fachpraktikum_k01589)
  * [Beschreibung](#beschreibung)
    + [Verwendete Techniken:](#verwendete-techniken)
    + [Funktionsweiße der Anwendung:](#funktionsweiße-der-anwendung)
      - [REST-API](#rest-api)


## Beschreibung

Bei der vorliegenden Anwendung handelt es sich um eine Java Spring Boot Anwendung, die einen Prototypen für eine Erklärungskomponente darstellt. Dabei werden Argumentationsbäume im AIF-Format eingelesen und verarbeitet. 


### Verwendete Techniken:

- Java 18
- Apache Jena Framework
- Spring Boot Framework
- Arguement Interchange Format 
- Ressource Description Framework


### Funktionsweiße der Anwendung:



Unter dem Package *view* findet man die Klasse *MainShower*. Wenn die Klasse ausgeführt wird öffnet sich ein Dialog, der alle hinterlegten Argumentationsbäume anzeigt. 

![image](https://user-images.githubusercontent.com/122731286/219120834-48efd5ba-eac5-4427-bd82-eefbb903c538.png)

Hier kann man sich einen Argumentationsbaum aus der Liste auswählen und sich anzeigen lassen.

![image](https://user-images.githubusercontent.com/122731286/219120686-4494cce3-071d-4eff-a372-f0adaa0bbdd4.png)

Zusätzlich ist es möglich die ArgStrenght der einzelnen Prämissen anzupassen, falls bei den I-Nodes eine ArgStrenght hinterlegt ist. 


Wenn man auf den Button *Dialog starten* klickt, dann öffnet sich ein Dialog bei dem der Patient/User gefragt wird, ob die jeweilige Prämisse der Arguementationsbäumen eintreffen, um so auf eine Conclusion zu schließen und eine Diagnose zu erstellen.

#### REST-API

folgt...



