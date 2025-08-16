// Add any client-side JavaScript functionality here
document.addEventListener('DOMContentLoaded', function() {
    // Initialize any necessary JavaScript
    console.log('Leave Management System loaded');
    
    // Add event listeners for form validation, etc.
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            // Basic form validation can be added here
            const requiredFields = form.querySelectorAll('[required]');
            let isValid = true;
            
            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    isValid = false;
                    field.style.borderColor = '#F44336';
                } else {
                    field.style.borderColor = '#ddd';
                }
            });
            
            if (!isValid) {
                e.preventDefault();
                alert('Please fill in all required fields.');
            }
        });
    });
    
    // Date validation for leave application
    const fromDateInput = document.querySelector('input[name="fromDate"]');
    const toDateInput = document.querySelector('input[name="toDate"]');
    
    if (fromDateInput && toDateInput) {
        fromDateInput.addEventListener('change', function() {
            if (this.value) {
                toDateInput.min = this.value;
                if (toDateInput.value && toDateInput.value < this.value) {
                    toDateInput.value = '';
                }
            }
        });
    }
});