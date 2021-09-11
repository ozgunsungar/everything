//Elementleri Seçmek

const regForm = document.querySelector("#reg-form");
const nameInput = document.querySelector("#inputName");
const surnameInput = document.querySelector("#inputSurname");
const mailInput = document.querySelector("#inputEmail3");
const phoneInput = document.querySelector("#tel-input");
const dateInput = document.querySelector("#date-input");
const passwInput = document.querySelector("#inputPassword3");
const cardBody = document.querySelector(".card-body");


eventListeners();
nameInput.value = "";
surnameInput.value = "";
phoneInput.value = "";
mailInput.value = "";
passwInput.value = "";

function eventListeners() {
    regForm.addEventListener("submit", addTodo);

}

function addTodo(e) {
    const name = nameInput.value.trim();
    const surname = surnameInput.value.trim();
    const phone = phoneInput.value;
    const mail = mailInput.value;
    const date = dateInput.value;
    const password = passwInput.value;
    
    //control değerleri
    const letters = /^[a-zA-ZğüşöçİĞÜŞÖÇ]+$/;
    const phoneNo = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
    const mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    const passwformat = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,16}$/;
    //date için
    const dateformat = /^\(?([0-9]{4})\)?[-. ]?([0-9]{2})[-. ]?([0-9]{2})$/;
    let dateArray = date.split('-');
    let dateControl = false;
    
    if(dateArray[0]>="1905" && dateArray[0]<="2020"){
        dateControl=true;
        
    }



    if (name.match(letters) && surname.match(letters) &&  phone.match(phoneNo) && mail.match(mailformat)
    && (date.match(dateformat) && dateControl) && password.match(passwformat)) {
        showAlert("success", "Kaydınız oluşturuldu");
    }
    else {
        if (!(name.match(letters))) {
            showAlert("danger", "Lütfen ismi doğru girin!");
            nameInput.value = "";
        }
        if (!(surname.match(letters))) {
            showAlert("danger", "Lütfen soyadı doğru girin!");
            surnameInput.value = "";
        }
        if (!(mail.match(mailformat))) {
            showAlert("danger", "Lütfen maili doğru girin!");
            mailInput.value = "";
        }
        if (!(phone.match(phoneNo))) {
            showAlert("danger", "Lütfen telefonu doğru girin!");
            phoneInput.value = "";
        }
        if (!(date.match(dateformat) && dateControl)) {
            showAlert("danger", "Lütfen tarihi doğru girin!");
            dateInput.value = "";
        }
        if (!(password.match(passwformat))) {
            showAlert("danger", "Lütfen şifreyi doğru girin!");
            passwInput.value = "";
        }
        

        
        
        

    }

    e.preventDefault();
}

function showAlert(type, message) {
    //Creating alert div
    const alert = document.createElement("div");
    alert.className = `alert alert-${type}`;
    alert.textContent = message;
    //Adding alert to firstCardBody

    cardBody.insertBefore(alert, cardBody.firstChild);

    setTimeout(function () {
        alert.remove();
    }, 1600);
}



