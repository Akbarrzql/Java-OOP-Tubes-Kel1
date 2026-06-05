const budgetButtons = document.querySelectorAll('.budget-buttons button');

budgetButtons.forEach(button => {

    button.addEventListener('click', () => {

        budgetButtons.forEach(btn => {
            btn.classList.remove('active');
        });

        button.classList.add('active');

    });

});

document.addEventListener("DOMContentLoaded", () => {

    const budgetButtons =
        document.querySelectorAll(".budget-buttons button");

    budgetButtons.forEach(button => {

        button.addEventListener("click", () => {

            budgetButtons.forEach(btn =>
                btn.classList.remove("active")
            );

            button.classList.add("active");

        });

    });

});