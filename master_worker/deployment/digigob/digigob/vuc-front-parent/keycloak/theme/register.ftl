<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "header">
        ${msg("registerTitle")}
    <#elseif section = "form">
        <form id="kc-register-form" class="${properties.kcFormClass!}" action="${url.registrationAction}" method="post">
            

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('firstName',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="fullName" class="${properties.kcLabelClass!}">${msg("fullName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="fullName" class="${properties.kcInputClass!}" name="fullName" value="${(register.formData.fullName!'')}" />
                </div>
            </div>
			<div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('firstName',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="dateAndPlaceOfBirth" class="${properties.kcLabelClass!}">${msg("dateAndPlaceOfBirth")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="dateAndPlaceOfBirth" class="${properties.kcInputClass!}" name="dateAndPlaceOfBirth" value="${(user.attributes.dateAndPlaceOfBirth!'')}" />
                </div>
            </div>
			<div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('firstName',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="NameOfYourParents" class="${properties.kcLabelClass!}">${msg("NameOfYourParents")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="NameOfYourParents" class="${properties.kcInputClass!}" name="NameOfYourParents" value="${(user.attributes.NameOfYourParents!'')}" />
                </div>
            </div>
			
			<div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="user.attributes.documentNumber" class="${properties.kcLabelClass!}">${msg("documentNumber")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="number" id="user.attributes.documentNumber" class="${properties.kcInputClass!}" name="user.attributes.documentNumber" value="${(user.attributes.documentNumber!'')}" />
                </div>
            </div>
			<div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="spousesNname" class="${properties.kcLabelClass!}">${msg("spousesNname")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="number" id="spousesNname" class="${properties.kcInputClass!}" name="spousesNname" value="${(user.attributes.spousesNname!'')}" />
                </div>
            </div>
			<div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="contactName" class="${properties.kcLabelClass!}">${msg("contactName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="number" id="contactName" class="${properties.kcInputClass!}" name="contactName" value="${(user.attributes.contactName!'')}" />
                </div>
            </div>
            <div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="nameOfBrotherSister" class="${properties.kcLabelClass!}">${msg("nameOfBrotherSister")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="number" id="nameOfBrotherSister" class="${properties.kcInputClass!}" name="nameOfBrotherSister" value="${(user.attributes.nameOfBrotherSister!'')}" />
                </div>
            </div>

            </#if>

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
