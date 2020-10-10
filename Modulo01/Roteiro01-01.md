## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1
**Prof. Delano M. Beder (UFSCar)**

**Módulo 01 - Tríade HTML, CSS e Javascript**

- - -

#### 01 - Exemplo da utilização Tríade: HTML5, CSS e Javascript
[Código](https://github.com/delanobeder/DSW1/blob/master/Modulo01/Triade)

- - -



1. Crie um diretório chamado **Triade**

2. Nesse diretório, crie o arquivo **index.html**

   ```html
   <!DOCTYPE html>
   <html>
       <head>
           <title>TODO supply a title</title>
           <meta charset="UTF-8">
           <meta name="viewport" content="width=device-width, initial-scale=1.0">
           <link rel = "stylesheet" type ="text/css" href = "estilo.css">
       </head>
       <body>
           <h1>Tag H1</h1>
           <p id="p1" class="odd">
               Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque malesuada lacus suscipit, dignissim erat at, congue quam. In ornare mauris ante, sed vehicula quam placerat id. Vivamus metus justo, gravida sed tristique id, maximus at ex.
           </p>
           <p id="p2" class="even">
               Donec lacinia mattis dolor, at vestibulum neque euismod ut. Suspendisse eleifend dui sed nisl malesuada, eget blandit massa aliquam. Praesent semper velit non magna luctus, quis gravida leo dapibus. Donec venenatis metus vitae auctor laoreet. Aenean eu arcu quis dolor rhoncus consequat.
           </p>
           <p id="p3" class="odd">
               Maecenas vestibulum condimentum leo non pellentesque. Integer malesuada non elit in malesuada. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc dictum commodo tortor ac porttitor.
           </p>
           <p id="p4" class="even">
               In hac habitasse platea dictumst. Proin non pellentesque magna, quis egestas nisl. Donec viverra orci vel eros sagittis, euismod luctus libero eleifend.
           </p>
           <p id="p5" class="odd">
               Quisque aliquam vel mauris nec molestie. Pellentesque facilisis at magna vitae placerat. Integer elit leo, blandit sed justo ut, malesuada eleifend urna.
               <span>
                   <b id="b1">Hide Even</b>
               </span>
           </p>
           <p id="p6" class="even">
               Integer vulputate porta erat, non posuere mauris venenatis in. In ac sapien hendrerit, vehicula est et, luctus mi. Etiam tincidunt lobortis semper. Proin eu dui porta, auctor odio vitae, mollis magna. Mauris posuere velit ac metus consequat pulvinar.
               <b>Negrito</b>
           </p>
           <h2>Tag H2</h2>
           <p class="odd">
               Cras at commodo nulla. Nam congue nisl id enim fringilla, a tincidunt nisl pulvinar. Morbi vehicula cursus iaculis. Aenean ut convallis lorem.
           </p>
           <p class="even">
               Vivamus molestie lacus vel interdum sagittis. Quisque nisi ipsum, fringilla at posuere ut, porta non orci. Maecenas suscipit interdum tempus. Quisque aliquet augue eget odio facilisis, nec efficitur sem bibendum.
           <p>
           <p class="odd">
               Nullam pulvinar, felis in pretium aliquam, elit elit varius enim, in tempor lacus leo vitae justo. Aenean tincidunt augue blandit nisl tempor molestie. Pellentesque sit amet ex nec nisl scelerisque egestas. Donec dignissim odio eget elit volutpat suscipit.
           </p>
           <p class="even">
               Integer hendrerit urna eu justo fermentum feugiat. Ut ullamcorper rhoncus tincidunt. Curabitur in justo consectetur lacus aliquet lacinia. Donec in dictum elit.
           </p>
           <p class="odd">
               Sed iaculis tellus ac neque tristique posuere. Curabitur nisl velit, laoreet ut condimentum non, dapibus quis eros. Proin accumsan auctor nibh, ac lacinia leo tincidunt vel.
           </p>
       </body>
   </html>
   ```


3. Crie o arquivo **estilo.css**

   ```css
   /* Seleciona todos os elementos p */
   
   p {
       color: blue;
   }
   ```
<div style="page-break-after: always"></div>

4. Teste (abra em um navegador) e verifique todos os parágrafos ficaram em "azul"

   

5. Adicione as seguintes regras no arquivo **estilo.css** e verifique as alterações nos parágrafos

   ```css
   /* Seleciona todos os elementos da classe even */
   
   .even {
       text-transform: capitalize;
       text-align: right;
   }
   
   /* Seleciona todos os elementos da classe odd */
   
   .odd {
       text-transform: uppercase;
       text-align: justify;
   }
   
   /* Seleciona os elementos com id = p3 ou id = p4 */
   
   #p3, #p4 {
       color: red;
   }
   ```

6. Adicione as seguintes regras no arquivo **estilo.css** e verifique as alterações nos trechos em negrito

   ```css
   /* Seleciona todo elemento "b" descendente (filho, neto, etc) de um elemento "p" */
   
   p b {
       color: green;
   }
   
   /* Seleciona todo elemento "b" filho de um elemento "p" */
   
   p > b {
       color: magenta;
   }
   ```

<div style="page-break-after: always"></div>

7. Adicione as seguintes regras no arquivo **estilo.css** e verifique as alterações nos últimos parágrafos

   ```css
   /* Seleciona todo elemento p imediatamente precedido de um elemento h2 */
   
   h2 + p {
       text-decoration: underline;
   }
   
   /* Seleciona todo elemento p precedido de um elemento h2 */
   
   h2 ~ p {
       color: darkmagenta;
   }
   ```



8. Altere o seguinte trecho no arquivo **index.html**

   ```html
   <!DOCTYPE html>
   <html>
       <head>
           <title>TODO supply a title</title>
           <meta charset="UTF-8">
           <meta name="viewport" content="width=device-width, initial-scale=1.0">
           <link rel = "stylesheet" type ="text/css" href = "estilo.css">
           <script src= "script.js" ></script>
       </head>
       <body>
           ...
           <p id="p5" class="odd">
               Quisque aliquam vel mauris nec molestie. Pellentesque facilisis at magna vitae placerat. Integer elit leo, blandit sed justo ut, malesuada eleifend urna.
               <span>
                   <b id="b1" onclick="hideEven()">Hide Even</b>
               </span>
           </p>
           ... restante do arquivo ...
       </body>
   </html>
   ```

<div style="page-break-after: always"></div>

9. Crie o arquivo **script.js**

   ```javascript
   function hideEven() {
       var flag = confirm('Você tem certeza?');
       if (flag) {
           var divsToHide = document.getElementsByClassName("even");
           for (var i = 0; i < divsToHide.length; i++) {
               divsToHide[i].style.visibility = "hidden"; 
               divsToHide[i].style.display = "none"; 
           }
       } else
           return false;
   } 
   ```


10. Testar novamente no browser (clique em cima do negrito **HIDE EVEN** e verifique o que acontece)


11. Fim


#### Leituras adicionais

- - -
- HTML Tutorial

  https://www.w3schools.com/html/

  

- CSS Tutorial

  https://www.w3schools.com/css/default.asp

  

- JavaScript Tutorial

  https://www.w3schools.com/js/default.asp

  

- Caelum: Desenvolvimento Web com HTML, CSS e JavaScript

  https://www.caelum.com.br/apostila-html-css-javascript/