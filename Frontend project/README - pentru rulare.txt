1. Instaleaza Nodejs (e doar pentru a deschide un server la localhost ca sa poti rula frontul)
https://nodejs.org/en/
2. Instaleaza Angular CLI - la linia de comanda 
npm install -g @angular/cli
3. Creeaza un nou proiect Angular. La linia de comanda tastezi
ng new towas
4. Schimbi current directory-ul din linia de comanda in locatia proiectului towas (cel creat) si instalezi Bootstrap 4 dand la linia de comanda:
npm i --save bootstrap
5. Copii din repo tot ce este peste proiectul creat local. 
6. De la linia de comanda dai 
ng serve
si deschizi serverul care la portul 4200 sta si asculta cererile tale.

Acesti pasi sunt utilizati pentru ca sunt multe pachete in proiectul de Angular si nu le pot pune in git ca sunt de dimensiuni mari (dureaza mult la push).