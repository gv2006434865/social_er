先到各個社群網站建立應用程式，取得應用程式的ID與密鑰，這樣才能透過Spring Social取得權限與資料。
Facebook的應用程式創建後，需要先設定「網域」及「隱私政策網址」，網域可填入利用phpMyAdmin架設的網址或其他網站的網址，隱私政策的產生則可以使用https://privacypolicies.com/ 來建置。
 
網域設定完畢，最後要針對OAuth來做調整，「強制網路OAuth重新驗」證須關閉，並在下方「有效的OAuth重新導向URL」中填入用來取得權限的網頁位址。
 
有效的導向設定才能成功進入透過Facebook取得資料，而Twitter的設定與Facebook的步驟相同。
接著前往https://spring.io/tools/sts/all 下載Spring Tool Suite(STS)並解壓縮，打開STS→File→Import Projects from File System or Archive，將檔案中的gs-accessing-data-neo4j與gs-accessing-facebook-complete加入STS，先執行gs-accessing-facebook-complete。
 
先將專案中放在\src\main\resources裡的application.properties參數做修正，修改為自己建立的應用程式的appid與密鑰。
 
執行專案後，到網頁上輸入http://localhost:8080/ 就能進入Spring Social的環境，分別取得不同社群網站的權限與資料。
再來就是gs-accessing-data-neo4j專案的參數修正，由於社群網站資料的取得限制與資安問題，所以我們的專案中已經放入範例資料，以那些資料來做實際執行的測試對向。此專案的執行是為了連結Neo4j資料庫，因此我們需要先到https://neo4j.com/download/ 下載執行檔，檔案中有包含Neo4j Desktop，我們可以透過Neo4j Desktop更輕鬆的控管Neo4j的使用參數。
 
在Neo4j Desktop建立一個新的資料庫並啟動，新資料庫的預設Http port與Spring Data Neo4j的設定相同，直接執行專案即可。
個體辨識的結果可以透過直接前往http://localhost:7474/browser/ 觀看，或是透過網站觀察圖表，打開資料夾中的Try資料夾，開啟首頁觀看合併結果與長條圖的簡易應用。
 
