fetch('http://localhost:8080/api/user/signIn', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        id: "email@email.com",
        password: "password"
    })
})
.then(response => response.json())
.then(data => {
    console.log("서버 응답:", data);
})
.catch(error => {
    console.error("에러 발생:", error);
});