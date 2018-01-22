var mouths = [ "Январь", "Февраль","Март","Апрель","Май","Июнь",
   "Июль","Август","Сентябрь","Октябрь", "Ноябрь","Декабрь"];

var sections = {
    clients: document.getElementById("clients"),
    orders: document.getElementById("orders"),
    seasonTickets: document.getElementById("seasonTickets"),
    trainings: document.getElementById("trainings"),
    trainers: document.getElementById("trainers"),
    sportPrograms: document.getElementById("sportPrograms")
};

var forms = {
    client: {
        table: document.getElementById("clientsTable"),
        form: document.getElementById("clientForm"),
        _form: document.getElementById("_clientForm"),
        id: document.getElementById("clientId"),
        clientName: document.getElementById("clientName"),
        clientPhone: document.getElementById("clientPhone")
    },
    order: {
        table: document.getElementById("ordersTable"),
        form: document.getElementById("orderForm"),
        _form: document.getElementById("_orderForm"),
        id: document.getElementById("orderId"),
        isPaid: document.getElementById("isPaid"),
        idClient: document.getElementById("idClient"),
        idSeasonTicket: document.getElementById("idSeasonTicket")
    },
    seasonTicket: {
        table: document.getElementById("seasonTicketsTable"),
        form: document.getElementById("seasonTicketForm"),
        _form: document.getElementById("_seasonTicketForm"),
        id : document.getElementById("seasonTicketId"),
        seasonTicketPrice: document.getElementById("seasonTicketPrice"),
        seasonTicketMouth: document.getElementById("seasonTicketMouth"),
        idTraining: document.getElementById("idTraining")
    },
    training: {
        table: document.getElementById("trainingsTable"),
        form: document.getElementById("trainingForm"),
        _form: document.getElementById("_trainingForm"),
        id: document.getElementById("trainingId"),
        trainingName: document.getElementById("trainingName"),
        idTrainer: document.getElementById("idTrainer"),
        idSportProgram: document.getElementById("idSportProgram")
    },
    trainer: {
        table: document.getElementById("trainersTable"),
        form: document.getElementById("trainerForm"),
        _form: document.getElementById("_trainerForm"),
        id: document.getElementById("trainerId"),
        trainerName: document.getElementById("trainerName"),
        trainerSalary: document.getElementById("trainerSalary")
    },
    sportProgram: {
        table: document.getElementById("sportProgramsTable"),
        form: document.getElementById("sportProgramForm"),
        _form: document.getElementById("_sportProgramForm"),
        id: document.getElementById("sportProgramId"),
        programName: document.getElementById("sportProgramName")
    }
};


var activeSection = "clients";

function switchSection(name) {
    sections[activeSection].style.display = "none";
    sections[name].style.display = "block";
    activeSection = name;
    load[name]();
}

function creatingClient() {
    forms.client.form.style.display = "block";
    forms.client.id.value = "";
    forms.client.clientName.value = "";
    forms.client.clientPhone.value = ""
}

forms.client.form.onclick = function(e) {
    if (e.target == forms.client.form) {
        forms.client.form.style.display = "none";
    }
};

function creatingOrder() {
    ensure(["clients", "seasonTickets"], function() {
        forms.order.form.style.display = "block";
        forms.order.id.value = "";
        forms.order.isPaid.checked = false;
        forms.order.idClient.value = "";
        forms.order.idClient.innerHTML = clients.map((c) => `<option value="${c.id}">${c.name}</option>`).join('');
        forms.order.idSeasonTicket.value = "";
        forms.order.idSeasonTicket.innerHTML = seasonTickets.map((s) => `<option value="${s.id}">${s.price}(${mouths[+s.mouth]})</option>`).join('');
    });
}

forms.order.form.onclick = function(e) {
    if (e.target == forms.order.form) {
        forms.order.form.style.display = "none";
    }
};

function creatingSeasonTicket() {
    ensure(["trainings"], function(){
        forms.seasonTicket.form.style.display = "block";
        forms.seasonTicket.id.value = "";
        forms.seasonTicket.seasonTicketMouth.innerHTML = mouths.map((m, i)=> `<option value="${i}">${m}</option>`).join('');
        forms.seasonTicket.seasonTicketMouth.value = "";
        forms.seasonTicket.seasonTicketPrice.value = "";
        forms.seasonTicket.idTraining.value = "";
        forms.seasonTicket.idTraining.innerHTML = trainings.map((t) => `<option value="${t.id}">${t.name}</option>`).join();
    });
}

forms.seasonTicket.form.onclick = function(e) {
    if (e.target == forms.seasonTicket.form) {
        forms.seasonTicket.form.style.display = "none";
    }
};

function creatingTraining() {
    ensure(["trainers", "sportPrograms"], function() {
        forms.training.form.style.display = "block";
        forms.training.id.value = "";
        forms.training.trainingName.value = "";
        forms.training.idTrainer.value = "";
        forms.training.idTrainer.innerHTML = trainers.map((t) => `<option value="${t.id}">${t.name}</option>`).join('');
        forms.training.idSportProgram.value = "";
        forms.training.idSportProgram.innerHTML = sportPrograms.map((s) => `<option value="${s.id}">${s.name}</option>`).join('');
    });
}

forms.training.form.onclick = function(e) {
    if (e.target == forms.training.form) {
        forms.training.form.style.display = "none";
    }
};

function creatingTrainer() {
    forms.trainer.form.style.display = "block";
    forms.trainer.id.value = "";
    forms.trainer.trainerName.value = "";
    forms.trainer.trainerSalary.value = ""
}

forms.trainer.form.onclick = function(e) {
    if (e.target == forms.trainer.form) {
        forms.trainer.form.style.display = "none";
    }
};

function creatingSportProgram() {
    forms.sportProgram.form.style.display = "block";
    // сброс полей
    forms.sportProgram.id.value = "";
    forms.sportProgram.programName.value = "";
}

forms.sportProgram.form.onclick = function(e) {
    if (e.target == forms.sportProgram.form) {
        forms.sportProgram.form.style.display = "none";
    }
};


function request(method, url, data, done) {
    if(typeof(done) == 'undefined') {
        done = function(){};
    }
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open(method, url);
    xmlhttp.onreadystatechange = function() {
        if (this.status != 200) {
            alert("JS Error: " + this.status);
            return;
        }
        if (xmlhttp.readyState == 4) {
            done(this.responseText);
        }
    }
    xmlhttp.send(data);
}

function ensure(list, done) {
    var i = 0;
    function loadNext(data){
        window[list[i]] = JSON.parse(data);
        i++;
        if(i == list.length) {
            return done();
        }
        request("GET", `/collections/${list[i]}`, null, loadNext);
    }
    request("GET", `/collections/${list[i]}`, null, loadNext)
}

// collections
var clients = [];
var orders = [];
var programs = [];
var trainings = [];
var sportPrograms = [];
var trainers = [];

var load = {
    clients: function() {
        var table = forms.client.table;
        request("GET", "/collections/clients", null,
            function(data) {
                clients = JSON.parse(data);
                table.innerHTML = '';
                for(var i = 0; i < clients.length; i++) {
                    var tr = document.createElement('tr');
                    tr.innerHTML =`<td>${clients[i].id}</td>
                                   <td>${clients[i].name}</td>
                                   <td>${clients[i].phoneNumber}</td>
                                   <td>${clients[i].orders.filter((o) => o.isPaid).length}/${clients[i].orders.length}</td>
                                   <td><button onclick="editClient(${clients[i].id})">Редактировать</button>
                                       <button onclick="deleteClient(${clients[i].id})">Удалить</button></td>`;
                    table.appendChild(tr);
                }
            });
    },
    orders: function() {
        var table = forms.order.table;
        request("GET", "/collections/orders", null,
            function(data) {
                orders = JSON.parse(data);
                table.innerHTML = '';
                for(var i = 0; i < orders.length; i++) {
                    var tr = document.createElement('tr');
                    tr.innerHTML =`<td>${orders[i].id}</td>
                                   <td>${orders[i].isPaid ? "Да":"Нет"}</td>
                                   <td>${orders[i].client.name}</td>
                                   <td>${orders[i].seasonTicket.price}</td>
                                   <td><button onclick="editOrder(${orders[i].id})">Редактировать</button>
                                       <button onclick="deleteOrder(${orders[i].id})">Удалить</button></td>`;
                    table.appendChild(tr);
                }
            });
    },
    seasonTickets: function() {
        var table = forms.seasonTicket.table;
        request("GET", "/collections/seasonTickets", null,
            function(data) {
                seasonTickets = JSON.parse(data);
                table.innerHTML = '';
                for(var i = 0; i < seasonTickets.length; i++) {
                    var tr = document.createElement('tr');
                    tr.innerHTML =`<td>${seasonTickets[i].id}</td>
                                   <td>${seasonTickets[i].price}</td>
                                   <td>${mouths[+seasonTickets[i].mouth]}</td>
                                   <td>${seasonTickets[i].orders.length}</td>
                                   <td>${seasonTickets[i].training.name}</td>
                                   <td><button onclick="editSeasonTicket(${seasonTickets[i].id})">Редактировать</button>
                                       <button onclick="deleteSeasonTicket(${seasonTickets[i].id})">Удалить</button></td>`;
                    table.appendChild(tr);
                }
            });
    },
    sportPrograms: function() {
        var table = forms.sportProgram.table;
        request("GET", "/collections/sportPrograms", null,
            function(data) {
                programs = JSON.parse(data);
                table.innerHTML = '';
                for(var i = 0; i < programs.length; i++) {
                    var tr = document.createElement('tr');
                    tr.innerHTML =`<td>${programs[i].id}</td>
                                   <td>${programs[i].name}</td>
                                   <td>${programs[i].trainings.map((t)=>t.name).join("<br/>")}</td>
                                   <td><button onclick="editSportProgram(${programs[i].id})">Редактировать</button>
                                       <button onclick="deleteSportProgram(${programs[i].id})">Удалить</button></td>`;
                    table.appendChild(tr);
                }
            });
    },
    trainers: function() {
        var table = forms.trainer.table;
        request("GET", "/collections/trainers", null,
            function(data) {
                trainers = JSON.parse(data);
                table.innerHTML = '';
                for(var i = 0; i < trainers.length; i++) {
                    var tr = document.createElement('tr');
                    tr.innerHTML =`<td>${trainers[i].id}</td>
                                   <td>${trainers[i].name}</td>
                                   <td>${trainers[i].salary}</td>
                                   <td>${trainers[i].trainings.map((t)=> t.name).join("<br />")}</td>
                                   <td><button onclick="editTrainer(${trainers[i].id})">Редактировать</button>
                                       <button onclick="deleteTrainer(${trainers[i].id})">Удалить</button></td>`;
                    table.appendChild(tr);
                }
            });
    },
    trainings: function() {
       var table = forms.training.table;
       request("GET", "/collections/trainings", null,
           function(data) {
               trainings = JSON.parse(data);
               table.innerHTML = '';
               for(var i = 0; i < trainings.length; i++) {
                   var tr = document.createElement('tr');
                   tr.innerHTML =`<td>${trainings[i].id}</td>
                                  <td>${trainings[i].name}</td>
                                  <td>${trainings[i].trainer.name}</td>
                                  <td>${trainings[i].sportProgram.name}</td>
                                  <td><button onclick="editTraining(${trainings[i].id})">Редактировать</button>
                                      <button onclick="deleteTraining(${trainings[i].id})">Удалить</button></td>`;
                   table.appendChild(tr);
               }
           });
   }
}

// send-edit-delete

forms.client._form.onsubmit = function sendClient(e) {
     var client = forms.client;
     var method = client.id.value !== '' ? "PUT" : "POST";
     var id = client.id.value;
     var name = client.clientName.value;
     var phone = client.clientPhone.value;
     request(method,
         "/collections/clients",
         `${id !== '' ? `${id};` : ''}${name};${phone}\n`,
         function done() {
             load.clients();
             client.form.style.display = "none";
         });
     e.preventDefault();
 }

 function editClient(id) {
     var client = clients.find((c) => c.id == id)
     forms.client.form.style.display = "block";
     forms.client.id.value = client.id;
     forms.client.clientName.value = client.name;
     forms.client.clientPhone.value = client.phoneNumber;
 }

 function deleteClient(id) {
     request("DELETE",
         "/collections/clients",
         `${id}\n`,
         load.clients);
 }

forms.order._form.onsubmit = function sendOrder(e) {
    var order = forms.order;
    var method = order.id.value !== '' ? "PUT" : "POST";
    var id = order.id.value;
    var isPaid = order.isPaid.checked ? "true" : "false";
    var idClient = order.idClient.value;
    var idSeasonTicket = order.idSeasonTicket.value;
    request(method,
        "/collections/orders",
        `${id !== '' ? `${id};` : ''}${isPaid};${idClient};${idSeasonTicket}\n`,
        function done() {
            load.orders();
            order.form.style.display = "none";
        });
    e.preventDefault();
}

function editOrder(id) {
    ensure(["clients", "seasonTickets"], function(){
        var order = orders.find((o) => o.id == id)
        forms.order.form.style.display = "block";
        forms.order.id.value = order.id;
        forms.order.isPaid.checked = order.isPaid;
        forms.order.idClient.innerHTML = clients.map((c) => `<option value=${c.id}>${c.name}</option>`).join('');
        forms.order.idSeasonTicket.innerHTML = seasonTickets.map((s) => `<option value="${s.id}">${s.price}(${mouths[+s.mouth]})</option>`).join('');
        forms.order.idClient.value = order.client.id;
        forms.order.idSeasonTicket.value = order.seasonTicket.id;
    });
}

function deleteOrder(id) {
    request("DELETE",
        "/collections/orders",
        `${id}\n`,
        load.orders);
}

forms.seasonTicket._form.onsubmit = function sendSeasonTicket(e) {
    var seasonTicket = forms.seasonTicket;
    var method = seasonTicket.id.value !== '' ? "PUT" : "POST";
    var id = seasonTicket.id.value;
    var price = seasonTicket.seasonTicketPrice.value;
    var mouth = seasonTicket.seasonTicketMouth.value;
    var idTraining = seasonTicket.idTraining.value;
    request(method,
        "/collections/seasonTickets",
        `${id !== '' ? `${id};` : ''}${price};${mouth};${idTraining}\n`,
        function done() {
            load.seasonTickets();
            seasonTicket.form.style.display = "none";
        });
    e.preventDefault();
}

function editSeasonTicket(id) {
    ensure(["trainings"], function(){
        var ticket = seasonTickets.find((s) => s.id == id);
        forms.seasonTicket.form.style.display = "block";
        forms.seasonTicket.id.value = ticket.id;
        forms.seasonTicket.seasonTicketMouth.innerHTML = mouths.map((m, i)=> `<option value="${i}">${m}</option>`).join('');
        forms.seasonTicket.seasonTicketMouth.value = ticket.mouth;
        forms.seasonTicket.seasonTicketPrice.value = ticket.price;
        forms.seasonTicket.idTraining.value = ticket.training.id;
        forms.seasonTicket.idTraining.innerHTML = trainings.map((t) => `<option value="${t.id}">${t.name}</option>`).join();
    });
}

function deleteSeasonTicket(id) {
    request("DELETE",
        "/collections/seasonTickets",
        `${id}\n`,
        load.seasonTickets);
}


forms.training._form.onsubmit = function sendTraining(e) {
    var training = forms.training;
    var method = training.id.value !== '' ? "PUT" : "POST";
    var id = training.id.value;
    var name = training.trainingName.value;
    var idTrainer = training.idTrainer.value;
    var idSportProgram = training.idSportProgram.value;
    request(method,
        "/collections/trainings",
        `${id !== '' ? `${id};` : ''}${name};${idSportProgram};${idTrainer}\n`,
        function done() {
            load.trainings();
            training.form.style.display = "none";
        });
    e.preventDefault();
}

function editTraining(id) {
    ensure(["trainers", "sportPrograms"], function() {
        var training = trainings.find((t) => t.id == id);
        forms.training.form.style.display = "block";
        forms.training.id.value = training.id;
        forms.training.trainingName.value = training.name;
        forms.training.idTrainer.innerHTML = trainers.map((t) => `<option value="${t.id}">${t.name}</option>`).join('');
        forms.training.idSportProgram.innerHTML = sportPrograms.map((s) => `<option value="${s.id}">${s.name}</option>`).join('');
        forms.training.idTrainer.value = training.trainer.id;
        forms.training.idSportProgram.value = training.sportProgram.id;
    });

}

function deleteTraining(id) {
    request("DELETE",
            "/collections/trainings",
            `${id}\n`,
            load.trainings);
}


forms.sportProgram._form.onsubmit = function sendSportProgram(e) {
    var program = forms.sportProgram;
    var method = program.id.value !== '' ? "PUT" : "POST";
    var id = program.id.value;
    var name = program.programName.value;
    request(method,
        "/collections/sportPrograms",
        `${id !== '' ? `${id};` : ''}${name}\n`,
        function done() {
            load.sportPrograms();
            program.form.style.display = "none";
        });
    e.preventDefault();
}

function editSportProgram(id) {
    var program = programs.find((c) => c.id == id)
    forms.sportProgram.form.style.display = "block";
    forms.sportProgram.id.value = program.id;
    forms.sportProgram.programName.value = program.name;
}

function deleteSportProgram(id) {
    request("DELETE",
        "/collections/sportPrograms",
        `${id}\n`,
        load.sportPrograms);
}

forms.trainer._form.onsubmit = function sendTrainer(e) {
    var trainer = forms.trainer;
    var method = trainer.id.value !== '' ? "PUT" : "POST";
    var id = trainer.id.value;
    var name = trainer.trainerName.value;
    var salary = trainer.trainerSalary.value;
    request(method,
        "/collections/trainers",
        `${id !== '' ? `${id};` : ''}${name};${salary}\n`,
        function done() {
            load.trainers();
            trainer.form.style.display = "none";
        });
    e.preventDefault();
}

function editTrainer(id) {
    var trainer = trainers.find((c) => c.id == id)
    forms.trainer.form.style.display = "block";
    forms.trainer.id.value = trainer.id;
    forms.trainer.trainerName.value = trainer.name;
    forms.trainer.trainerSalary.value = trainer.salary;
}

function deleteTrainer(id) {
    request("DELETE",
        "/collections/trainers",
        `${id}\n`,
        load.trainers);
}


switchSection("clients")