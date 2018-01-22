function Request(){
    try{
        var data =  document.getElementById("inputStroke").value;
        SendText(data);
        } catch(err){
            alert("Вы не ввели данные или ввели их неверно, повторите попытку.");
        }
    return false;
}

function SendText(lineText){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open('POST', '/chet-ne-chet', false);
    xmlhttp.onreadystatechange = function() {
          if (this.status != 200) {
            alert("Error");
            return;
          }
          document.getElementById('result').value = this.responseText;
    }
    xmlhttp.send(lineText + "\n");
}

