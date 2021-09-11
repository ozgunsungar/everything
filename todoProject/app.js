//Elementleri Seçmek

const todoForm = document.querySelector("#todo-form");
const todoInput = document.querySelector("#todo-input");
const todoFilter = document.querySelector("#todo-filter");
const firstCardBody = document.querySelectorAll(".card-body")[0];
const secondCardBody = document.querySelectorAll(".card-body")[1];
const todoList = document.querySelector(".list-group")
const deleteBtn = document.querySelector("#delete-all");

eventListeners();
todoInput.value = "";
todoFilter.value = "";

function eventListeners() {
    todoForm.addEventListener("submit", addTodo);
    secondCardBody.addEventListener("click", removeItem);
    todoFilter.addEventListener("keyup", filterTodos);
    deleteBtn.addEventListener("click", deleteAll);
    window.addEventListener("DOMContentLoaded", loadItemsFromStorage);


}

function addTodo(e) {
    const todo = todoInput.value.trim();

    if (todo === "") {
        showAlert("danger", "Lütfen geçerli bir Todo giriniz!");
    }
    else {
        if (!control(todo)) {

            addTodoToUI(todo);
            addTodoToStorage(todo);
            showAlert("success", "Todo Başarıyla Eklendi!");
            todoInput.value = "";


        } else {
            showAlert("info", "Ekleyeceğiniz Todo Zaten Eklenmiş!");
        }
    }

    e.preventDefault();
}

function control(newTodo) {
    let todos = getTodosFromStorage();
    isHave = false;
    todos.forEach(function (todo) {
        if (todo === newTodo) {
            isHave = true;
        }
    });
    return isHave;

}
function addTodoToStorage(todo) {
    if (todo != "") {
        let todos = getTodosFromStorage();
        todos.push(todo);
        localStorage.setItem("todos", JSON.stringify(todos));


    }
}

function loadItemsFromStorage() {
    let todos = getTodosFromStorage();
    todos.forEach(function (todo) {

        addTodoToUI(todo);
    })
}
function showAlert(type, message) {
    //Creating alert div
    const alert = document.createElement("div");
    alert.className = `alert alert-${type}`;
    alert.textContent = message;
    //Adding alert to firstCardBody


    firstCardBody.appendChild(alert);

    setTimeout(function () {
        alert.remove();
    }, 1500);
}

function getTodosFromStorage() {
    let todos;
    if (localStorage.getItem("todos") === null) {
        todos = [];
    } else {
        todos = JSON.parse(localStorage.getItem("todos"));

    }
    return todos;
}
function addTodoToUI(newTodo) {


    //     <li class="list-group-item d-flex justify-content-between">Todo1
    //     <a href="#" class="delete-item"><i class="fa fa-remove"></i></a>
    // </li>
    //Creating listItem

    let listItem = document.createElement("li");
    listItem.className = "list-group-item d-flex justify-content-between";
    //Adding Text
    listItem.appendChild(document.createTextNode(newTodo));

    //Creating listLink
    let listLink = document.createElement("a");
    listLink.href = "#";
    listLink.className = "delete-item";
    listLink.innerHTML = "<i class='fa fa-remove'></i>";

    //Adding link to item
    listItem.appendChild(listLink);
    //Adding item to list
    todoList.insertBefore(listItem, todoList.firstChild);




}
function removeItem(e) {

    if (e.target.className === "fa fa-remove") {
        e.target.parentElement.parentElement.remove();
        deleteFromStorage(e.target.parentElement.parentElement.textContent);
    }
}
function deleteFromStorage(text) {
    let todos = getTodosFromStorage();
    todos.forEach(function (todo, index) {
        if (text === todo) {
            todos.splice(index, 1)
            localStorage.setItem("todos", JSON.stringify(todos));
            showAlert("success", "Todo Başarıyla Silindi!")
        }
    });
}
function filterTodos(e) {
    const filterInput = todoFilter.value.toLowerCase();
    const listItems = document.querySelectorAll(".list-group-item");
    listItems.forEach(function (item) {
        const text = item.textContent.toLowerCase();
        if (text.indexOf(filterInput) === -1) {
            //Bulamadı
            item.setAttribute("style", "display: none!important;");
        } else {
            item.setAttribute("style", "display:block;");
        }
    });
}
function deleteAll(e) {
    if (todoList.firstElementChild != null) {

        if (window.confirm("Emin Misin?")) {
            while (todoList.firstElementChild != null) {
                todoList.removeChild(todoList.firstElementChild);
            }
            localStorage.removeItem("todos");
            showAlert("success", "Bütün Todolar Başarıyla Silindi!");
        }

    } else {
        showAlert("info", "Silinecek Todo Bulunmamaktadır...");
    }



}

