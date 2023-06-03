window.addEventListener('load', () => {
    //gets the 'id' column for the given row and directs to that item's edit link
    $(".data-row").on("click", (e) => {
        let id=$(e.target).closest("tr").children(".id").html()
        window.location.href = `/edit/${id}`;
    })
});