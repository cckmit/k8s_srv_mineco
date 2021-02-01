CKEDITOR.editorConfig = function( config )
{
    config.readOnly = true;
    config.extraPlugins = 'uicolor';
    config.resize_enabled = false;
    config.scayt_autoStartup = true;

   config.toolbar_emptyToolbar =
[
         { name: 'empty', items : [] }
];
};
