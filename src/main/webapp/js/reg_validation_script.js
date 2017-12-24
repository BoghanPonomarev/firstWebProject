/* ----------------------------

	CustomValidation prototype

	- Keeps track of the list of invalidity messages for this input
	- Keeps track of what validity checks need to be performed for this input
	- Performs the validity checks and sends feedback to the front end

---------------------------- */

function CustomValidation(input) {
    this.invalidities = [];
    this.validityChecks = [];

    //add reference to the input node
    this.inputNode = input;

    //trigger method to attach the listener
    this.registerListener();
}

CustomValidation.prototype = {
    addInvalidity: function (message) {
        this.invalidities.push(message);
    },
    getInvalidities: function () {
        return this.invalidities.join('. \n');
    },
    checkValidity: function (input) {
        for (var i = 0; i < this.validityChecks.length; i++) {

            var isInvalid = this.validityChecks[i].isInvalid(input);
            if (isInvalid) {
                this.addInvalidity(this.validityChecks[i].invalidityMessage);
            }

            var requirementElement = this.validityChecks[i].element;

            if (requirementElement) {
                if (isInvalid) {
                    requirementElement.classList.add('invalid');
                    requirementElement.classList.remove('valid');
                } else {
                    requirementElement.classList.remove('invalid');
                    requirementElement.classList.add('valid');
                }

            } // end if requirementElement
        } // end for
    },
    checkInput: function () { // checkInput now encapsulated

        this.inputNode.CustomValidation.invalidities = [];
        this.checkValidity(this.inputNode);

        if (this.inputNode.CustomValidation.invalidities.length === 0 && this.inputNode.value !== '') {
            this.inputNode.setCustomValidity('');
        } else {
            var message = this.inputNode.CustomValidation.getInvalidities();
            this.inputNode.setCustomValidity(message);
        }
    },
    registerListener: function () { //register the listener here

        var CustomValidation = this;

        this.inputNode.addEventListener('keyup', function () {
            CustomValidation.checkInput();
        });


    }

};


/* ----------------------------

	Validity Checks

	The arrays of validity checks for each input
	Comprised of three things
		1. isInvalid() - the function to determine if the input fulfills a particular requirement
		2. invalidityMessage - the error message to display if the field is invalid
		3. element - The element that states the requirement

---------------------------- */

var usernameValidityChecks = [
    {
        isInvalid: function (input) {
            return input.value.length < 5 | input.value.length > 15;
        },
        invalidityMessage: 'This input needs to be between 5 and 15 characters',
        element: document.querySelector('label[for="login"] .input-requirements li:nth-child(1)')
    },
    {
        isInvalid: function (input) {
            return input.value.match(/[^a-zA-Z0-9]/g);
        },
        invalidityMessage: 'Only letters and numbers are allowed',
        element: document.querySelector('label[for="login"] .input-requirements li:nth-child(2)')
    }
];

var emailValidityChecks = [
    {
        isInvalid: function (input) {
            return !input.value.match(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
        },
        invalidityMessage: 'Unprotect email format',
        element: document.querySelector('label[for="email"] .input-requirements li:nth-child(1)')
    }
];
var phoneValiditiChecks= [
    {
        isInvalid: function (input) {
            return !input.value.match(/^\d{10}|(?:\d{3}-){2}\d{4}|\(\d{3}\)\d{3}-?\d{4}$/im);
        },
        invalidityMessage: 'Phone must have correct format',
        element: document.querySelector('label[for="phone_notification"] .input-requirements li:nth-child(1)')
    }
];
var passwordValidityChecks = [
    {
        isInvalid: function (input) {
            return input.value.length < 5 | input.value.length > 15;
        },
        invalidityMessage: 'This input needs to be between 5 and 15 characters',
        element: document.querySelector('label[for="password"] .input-requirements li:nth-child(1)')
    },
    {
        isInvalid: function (input) {
            return input.value.match(/[^a-zA-Z0-9\-\_]/g);
        },
        invalidityMessage: 'Password can consider only letters and numbers',
        element: document.querySelector('label[for="password"] .input-requirements li:nth-child(2)')
    }
];

var passwordRepeatValidityChecks = [
    {
        isInvalid: function () {
            return passwordRepeatInput.value != passwordInput.value;
        },
        invalidityMessage: 'This password needs to match the first one',
        element: document.querySelector('label[for="password_repeat"] .input-requirements li:nth-child(1)')
    }
];



/* ----------------------------

	Setup CustomValidation

	Setup the CustomValidation prototype for each input
	Also sets which array of validity checks to use for that input

---------------------------- */

var usernameInput = document.getElementById('login');
var emailInput = document.getElementById('email');
var phoneInput = document.getElementById('phone_notification');
var passwordInput = document.getElementById('password');
var passwordRepeatInput = document.getElementById('password_repeat');

usernameInput.CustomValidation = new CustomValidation(usernameInput);
usernameInput.CustomValidation.validityChecks = usernameValidityChecks;

emailInput.CustomValidation = new CustomValidation(emailInput);
emailInput.CustomValidation.validityChecks = emailValidityChecks;

phoneInput.CustomValidation = new CustomValidation(phoneInput);
phoneInput.CustomValidation.validityChecks = phoneValiditiChecks;

    passwordInput.CustomValidation = new CustomValidation(passwordInput);
passwordInput.CustomValidation.validityChecks = passwordValidityChecks;

passwordRepeatInput.CustomValidation = new CustomValidation(passwordRepeatInput);
passwordRepeatInput.CustomValidation.validityChecks = passwordRepeatValidityChecks;


/* ----------------------------

	Event Listeners

---------------------------- */

var inputs = document.querySelectorAll('input:not([type="submit"])');


var submit = document.querySelector('input[type="submit"');
var form = document.getElementById('registration');

function validate() {
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].CustomValidation.checkInput();
    }
}

submit.addEventListener('click', validate);
form.addEventListener('submit', validate);
