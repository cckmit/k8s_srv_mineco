<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "header">
        ${msg("registerTitle")}
    <#elseif section = "form">
        <form id="kc-register-form" class="${properties.kcFormClass!}" action="${url.registrationAction}" method="post">

        <div class="${properties.kcFormGroupClass!}">
            <div class="${properties.kcLabelWrapperClass!}">
                <label for="user.attributes.documentType" class="${properties.kcLabelClass!}">${msg("documentType")}</label>
            </div>
            <div class="${properties.kcInputWrapperClass!}">
                <select id="user.attributes.documentType" class="${properties.kcInputClass!}" name="user.attributes.documentType" value="DUI">
                    <option value="DUI" selected="selected">DUI</option>
                </select>
            </div>
        </div>

          <#if !realm.registrationEmailAsUsername>
            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('username',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="username" class="${properties.kcLabelClass!}">${msg("documentNumber")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="number" id="username" class="${properties.kcInputClass!}" name="username" value="${(register.formData.username!'')}" autocomplete="username" placeholder="${msg("documentNumberPlaceholder")}"/>
                </div>
            </div>
          </#if>

            <#if passwordRequired??>
            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('password',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="password" class="${properties.kcLabelClass!}">${msg("password")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="password" id="password" class="${properties.kcInputClass!}" name="password" autocomplete="new-password" placeholder="${msg("passwordPlaceholder")}"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('password-confirm',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="password-confirm" class="${properties.kcLabelClass!}">${msg("passwordConfirm")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="password" id="password-confirm" class="${properties.kcInputClass!}" name="password-confirm" placeholder="${msg("rePasswordPlaceholder")}"/>
                </div>
            </div>
            </#if>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('firstName',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="firstName" class="${properties.kcLabelClass!}">${msg("firstName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="firstName" class="${properties.kcInputClass!}" name="firstName" value="${(register.formData.firstName!'')}" placeholder="${msg("namePlaceholder")}"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('lastName',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="lastName" class="${properties.kcLabelClass!}">${msg("lastName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="lastName" class="${properties.kcInputClass!}" name="lastName" value="${(register.formData.lastName!'')}" placeholder="${msg("lastnamePlaceholder")}"/>
                </div>
            </div>

        <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('user.attributes.gender',properties.kcFormGroupErrorClass!)}">
            <div class="${properties.kcLabelWrapperClass!}">
                <label for="user.attributes.gender" class="${properties.kcLabelClass!}">${msg("gender")}</label>
            </div>
            <div class="${properties.kcInputWrapperClass!}">
                <select id="user.attributes.gender" class="${properties.kcInputClass!}" name="user.attributes.gender" required value="${(register.formData['user.attributes.gender']!'')}">
                    <option disabled selected="selected">${msg("selectGender")}</option>
                    <option value="MASCULINO">${msg("male")}</option>
                    <option value="FEMENINO">${msg("female")}</option>
                </select>
            </div>
        </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('email',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="email" class="${properties.kcLabelClass!}">${msg("email")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="email" class="${properties.kcInputClass!}" name="email" value="${(register.formData.email!'')}" autocomplete="email" placeholder="${msg("emailPlaceholder")}"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('user.attributes.birthDate',properties.kcFormGroupErrorClass!)}"">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="user.attributes.birthDate" class="${properties.kcLabelClass!}">${msg("birthDate")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="date" id="user.attributes.birthDate" class="${properties.kcInputClass!}" name="user.attributes.birthDate" value="${(register.formData['user.attributes.birthDate']!'')}"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('user.attributes.birthPlace',properties.kcFormGroupErrorClass!)}"">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="user.attributes.birthPlace" class="${properties.kcLabelClass!}">${msg("birthPlace")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="user.attributes.birthPlace" class="${properties.kcInputClass!}" name="user.attributes.birthPlace" placeholder="${msg("birthPlacePlaceholder")}" value="${(register.formData['user.attributes.birthPlace']!'')}"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('user.attributes.fatherName',properties.kcFormGroupErrorClass!)}"">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="user.attributes.fatherName" class="${properties.kcLabelClass!}">${msg("fatherName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="user.attributes.fatherName" class="${properties.kcInputClass!}" name="user.attributes.fatherName" placeholder="${msg("fatherNamePlaceholder")}" value="${(register.formData['user.attributes.fatherName']!'')}"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('user.attributes.motherName',properties.kcFormGroupErrorClass!)}"">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="user.attributes.motherName" class="${properties.kcLabelClass!}">${msg("motherName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="user.attributes.motherName" class="${properties.kcInputClass!}" name="user.attributes.motherName" placeholder="${msg("fatherNamePlaceholder")}" value="${(register.formData['user.attributes.motherName']!'')}"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('user.attributes.spousesName',properties.kcFormGroupErrorClass!)}"">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="user.attributes.spousesName" class="${properties.kcLabelClass!}">${msg("spousesName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="user.attributes.spousesName" class="${properties.kcInputClass!}" name="user.attributes.spousesName" placeholder="${msg("fatherNamePlaceholder")}" value="${(register.formData['user.attributes.spousesName']!'')}"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('user.attributes.brothersName',properties.kcFormGroupErrorClass!)}"">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="user.attributes.brothersName" class="${properties.kcLabelClass!}">${msg("brothersName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="user.attributes.brothersName" class="${properties.kcInputClass!}" name="user.attributes.brothersName" placeholder="${msg("fatherNamePlaceholder")}" value="${(register.formData['user.attributes.brothersName']!'')}"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('user.attributes.contactName',properties.kcFormGroupErrorClass!)}"">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="user.attributes.contactName" class="${properties.kcLabelClass!}">${msg("contactName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="user.attributes.contactName" class="${properties.kcInputClass!}" name="user.attributes.contactName" placeholder="${msg("fatherNamePlaceholder")}" value="${(register.formData['user.attributes.contactName']!'')}"/>
                </div>
            </div>

            <#if recaptchaRequired??>
            <div class="form-group">
                <div class="${properties.kcInputWrapperClass!}">
                    <div class="g-recaptcha" data-size="compact" data-sitekey="${recaptchaSiteKey}"></div>
                </div>
            </div>
            </#if>

            <div class="${properties.kcFormGroupClass!}">
                <div id="kc-form-options" class="${properties.kcFormOptionsClass!}">
                    <div class="${properties.kcFormOptionsWrapperClass!}">
                        <span><a href="${url.loginUrl}">${kcSanitize(msg("backToLogin"))?no_esc}</a></span>
                    </div>
                </div>

                <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                    <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("doRegister")}"/>
                </div>
            </div>
        </form>
    </#if>
</@layout.registrationLayout>
