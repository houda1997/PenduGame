          _____                   _______                  _______         
         /\    \                 /::\    \                /::\    \        
        /::\    \               /::::\    \              /::::\    \       
       /::::\    \             /::::::\    \            /::::::\    \      
      /::::::\    \           /::::::::\    \          /::::::::\    \     
     /:::/\:::\    \         /:::/~~\:::\    \        /:::/~~\:::\    \    
    /:::/__\:::\    \       /:::/    \:::\    \      /:::/    \:::\    \   
   /::::\   \:::\    \     /:::/    / \:::\    \    /:::/    / \:::\    \  
  /::::::\   \:::\    \   /:::/____/   \:::\____\  /:::/____/   \:::\____\ 
 /:::/\:::\   \:::\____\ |:::|    |     |:::|    ||:::|    |     |:::|    |
/:::/  \:::\   \:::|    ||:::|____|     |:::|    ||:::|____|     |:::|    |
\::/    \:::\  /:::|____| \:::\    \   /:::/    /  \:::\    \   /:::/    / 
 \/_____/\:::\/:::/    /   \:::\    \ /:::/    /    \:::\    \ /:::/    /  
          \::::::/    /     \:::\    /:::/    /      \:::\    /:::/    /   
           \::::/    /       \:::\__/:::/    /        \:::\__/:::/    /    
            \::/____/         \::::::::/    /          \::::::::/    /     
             ~~                \::::::/    /            \::::::/    /      
                                \::::/    /              \::::/    /       
                                 \::/____/                \::/____/        
                                  ~~                       ~~              
Copyright (C) 2017 équipe POO (ESI, Alger, Algérie)

Ce fichier est déstiné pour tester le TP POO 2017. 
Un TP concernant la conception et réalisation d'un jeu pour apprendre les 
mots d'une langue (selon le contenu de ce fichier) en utilisant trois types 
de questions: synonymes, antonymes et définitions.

===========================
SPECIFICATIONS TECHNIQUES:
===========================

Type de fichier: Un fichier texte
Encodage du fichier: UTF-8
Fin de ligne: Windows/Dos

----------
Encodage:
----------
Pour lire un fichier texte, on peut utiliser un BufferedReader:
BufferedReader in = 
     new BufferedReader(new FileReader("mots.poo"));

Mais, lorsque notre fichier est encodé avec un autre encodage, on doit spécifier cet encodage:
BufferedReader in = 
     new BufferedReader(new InputStreamReader(new FileInputStream("mots.poo"), "UTF-8"));

Pourquoi UTF-8? puisqu'il y a des caractères comme é, à, etc. Aussi, 


-------------
Fin de ligne:
-------------
Les systèmes Unix utilisent \n
Windows utilise \r\n
Mac utilise \r (anciennes versions), \n (nouvelles versions)

readLine() de la classe BufferedReader fonctionne sans avoir besoin de spécifier 
le type de fin de ligne. Cette fonction revoie une chaîne de charactères sans \n et \r



====================
CONTENU DU FICHIER:
====================

Le fichier contient une question sur un mot par ligne. Chaque ligne est de la forme:

TYPE;question;réponse

Exemple:
--------
SYNONYME;combat;bataille

Vous pouvez enrichir le fichier pour avoir plus de questions 


BONNE CHANCE!
