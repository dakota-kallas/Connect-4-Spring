
document.addEventListener("DOMContentLoaded", () => {
	let submit = document.getElementById("create-game");
	submit.onclick = (event)=>createGame(event);
});

function createGame(event) {
	event.preventDefault();
	
	let gameBody = {playerToken: "Monkey", computerToken: "Western"};
	
	fetch( "/?color=fff000", { method : "POST", body : gameBody } ).then( res => res.json() ).then( (res)=> {
		console.log(JSON.stringify(res));
	});	
}

