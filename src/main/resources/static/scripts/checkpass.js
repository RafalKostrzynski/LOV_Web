function checkPassword(inputPassword, inputUsername)
{
    var passw = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{9,20}$/;
    var username = /^{3,15}$/;
    if(inputPassword.value.match(passw) && inputUsername.value.match(username))
    {
        document.getElementById("wrongpass").textContent="Password must contain between 9 to 20 characters, at least one numeric digit, one uppercase and one lowercase letter!";
        return true;
    }
    else if(!inputPassword.value.match(passw) && !inputUsername.value.match(username))
    {
        document.getElementById("wrongpass").textContent="Password must contain between 9 to 20 characters, at least one numeric digit, one uppercase and one lowercase letter!";
        document.getElementById("wrongusername").textContent="Username must contain between 3 to 15 characters!";
        return false;
    }
    else if(!inputPassword.value.match(passw) && inputUsername.value.match(username))
    {
        document.getElementById("wrongpass").textContent="Password must contain between 9 to 20 characters, at least one numeric digit, one uppercase and one lowercase letter!";
        return false;
    }
    else if(inputPassword.value.match(passw) && !inputUsername.value.match(username))
    {
        document.getElementById("wrongusername").textContent="Username must contain between 3 to 15 characters!";
        return false;
    }
}