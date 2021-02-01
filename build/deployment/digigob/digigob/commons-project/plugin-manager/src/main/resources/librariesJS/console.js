if (typeof console !== 'object') {
    console = {};
}

if (typeof window !== 'object') {
    window = {};
}


(function(){
	if (typeof console.log !== 'function') {
        console.log = function (data) {
            print(data);
        };
    }
}())