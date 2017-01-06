//setTableHeader("Hola desde Javascript")

var url = 'http://localhost:8080/mock';
var myObject = JSON.parse(getJSON(url))

var table = document.getElementsByTagName('table')[0];


for (var jsonIndex in Object.keys(myObject)) {
    var row = table.insertRow();

    for (var keyIndex in Object.keys(myObject[jsonIndex])){
        var cell = row.insertCell();
        cell.innerHTML = Object.values(myObject[jsonIndex])[keyIndex];
     }

}

document.getElementById("json").innerHTML = JSON.stringify(myObject, null, 2);

function getJSON(url) {
    var resp ;
    var xmlHttp ;
    resp  = '' ;
    xmlHttp = new XMLHttpRequest();
    if(xmlHttp != null)
    {
        xmlHttp.open( "GET", url, false );
        xmlHttp.send();
        resp = xmlHttp.responseText;
    }
    return resp ;
}

function setTableHeader(text) {
    var table = document.getElementsByTagName('table')[0]// Create an empty <thead> element and add it to the table:
    var header = table.createTHead();

// Create an empty <tr> element and add it to the first position of <thead>:
    var row = header.insertRow(0);

// Insert a new cell (<td>) at the first position of the "new" <tr> element:
    var cell = row.insertCell(0);

// Add some bold text in the new cell:
    cell.innerHTML = "<b>"+text+"</b>";

    var td = document.querySelector('td');
    td.setAttribute('colspan', 3);

}


