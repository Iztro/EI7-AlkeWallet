// scripts.js

// Función para mostrar mensajes de error en el formulario
function showError(message) {
    var errorDiv = document.createElement('div');
    errorDiv.className = 'alert';
    errorDiv.textContent = message;
    document.querySelector('.container').prepend(errorDiv);
}

// Ejemplo de cómo manejar el envío del formulario
document.addEventListener('DOMContentLoaded', function () {
    var form = document.querySelector('form');
    if (form) {
        form.addEventListener('submit', function (event) {
            // Aquí puedes agregar validaciones adicionales antes de enviar el formulario
            var inputs = form.querySelectorAll('input');
            inputs.forEach(function (input) {
                if (input.value.trim() === '') {
                    showError('Todos los campos son obligatorios');
                    event.preventDefault();
                }
            });
        });
    }
});
