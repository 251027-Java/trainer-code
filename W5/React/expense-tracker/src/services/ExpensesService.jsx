const ExpensesService = {
    baseUrl: "http://localhost:3000/expenses",

// GET, PUT, POST, PATCH, DELETE
    async getAll() {
        console.log(this.baseUrl);
        const response = await fetch(this.baseUrl);
        console.log(response);
        if(!response.ok) throw new Error('Failed to fetch!');
        return response.json();
    },
};

export default ExpensesService;