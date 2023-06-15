function renderNal(data, type, row) {

    return data ? "НАЛ" : "БЕЗНАЛ";
}

function renderOperation(data, type, row) {

    return data ? "НАКОП" : "СПИС";
}

function renderDigits(data, type, row) {
    return data.toFixed(2);
}

function renderAzsName(data, type, row) {
    let azsName = "";
    $.each(data, function (key, value){
        if (key === "azsName" || key === "properties"){
            if (azsName.length > 0) {
                azsName += ", ";
            }
            azsName += value
        }
    });
    return azsName;
}

function renderFuel(data, type, row) {
    switch (data) {
        case "AI_92":
            return "АИ-92";
        case "DIESEL":
            return "ДТ";
        case "SUG":
            return "СУГ";
        case "AI_95":
            return "АИ-95";
    }
}

const changeHandler = e => {
    const value = e.value
    e.value = value.replace(/[^0-9,]/g, '')
}