/**
* This function will get JSONObject and
* create listview items to show user's items.
* @param result Contains all user items from database
*/
function displayItemsList(result) {
    $('#itemsList li').remove(); //clear screen

    let jsonObject = JSON.parse(result);
    let myItems = jsonObject.myitems;

    for (let i = 0; i < myItems.length; i++) {
        let myItemName = myItems[i].ITEM_NAME;
        let myItemCategory = myItems[i].ITEM_CATEGORY;
        let myItemCost = myItems[i].ITEM_COST;
        let myItemDate = myItems[i].ITEM_DATE;
        alert(myItemName, myItemCategory, myItemCost, myItemDate);
        $('#itemsList').append(newItem(myItemName, myItemCategory, myItemCost, myItemDate, myItems[i].ID)).trigger('create'); //add new item
    }
    $('#itemsList').listview("refresh");
}

/**
* gets category from selected name in Drop Down menu
* @param select selected text
*/
function getCategory(select) {
    category = select.options[select.selectedIndex].text;
}

/**
 * This function sends the user's input fields data
 * to the ViewModel for saving a new item
 */
function fetchItem() {
    var name = document.getElementById('itemname').value;
    document.getElementById("itemname").required = true;

    var cost = document.getElementById('cost').value;
    document.getElementById("cost").required = true;

    var date = document.getElementById('date').value;
    document.getElementById("date").required = true;
    clearForm();
    window.viewModel.addItem(name, category, cost, date);
}
/**
 * This function clears fields from the adding page
 */
function clearForm() {
    $('#itemname').val("");
    $('#cost').val("");
}

/**
 * This function calls the viewModel to get
 * all items for the Add Item page
 */
function showItemsUI() {
    window.viewModel.showItems();
}

/**
 * This function gets a specific item's id (from the listview)
 * and notify the ViewModel to delete that specific item
 * @param id a specific id item from the listview
 */
function deleteItem(id) {
    $('#' + id).remove();
    window.viewModel.deleteItemVM(id);
}

/**
 * This function generates dynamically a new item
 * element for the listview
 * with given details from "displayItemsList" function
 * @param name item's name
 * @param category item's category
 * @param cost item's cost
 * @param date item's date
 * @param id A specific id to attach for the new item element
 * @returns {HTMLLIElement}
 */
function newItem(name, category, cost, date, id) {
//creating li tag
    let newItem = document.createElement("li");
    newItem.setAttribute("data-role", "collapsible");
    newItem.setAttribute("data-collapsed-icon", "edit");
    newItem.setAttribute("data-iconpos", "right");
    newItem.setAttribute("data-inset", "false");
    newItem.id = id;

//creating h2 tag
    let itemTitle = document.createElement("h2");
    let contentTitle = document.createTextNode(name);
    itemTitle.appendChild(contentTitle);

//creating div tag (to be a wrapper)
    let wrapper = document.createElement("div");

//creating input tag
    let nameFieldInput = document.createElement("input");
    nameFieldInput.setAttribute("type", "text");
    nameFieldInput.setAttribute("value", name);
    nameFieldInput.id = "name" + id;

//creating input tag
    let categoryFieldInput = document.createElement("input");
    categoryFieldInput.setAttribute("type", "text");
    categoryFieldInput.setAttribute("value", category);
    categoryFieldInput.id = "category" + id;

//creating input tag
    let costFieldInput = document.createElement("input");
    costFieldInput.setAttribute("type", "text");
    costFieldInput.setAttribute("value", cost);
    costFieldInput.id = "cost" + id;

//creating input tag
    let dateFieldInput = document.createElement("input");
    dateFieldInput.setAttribute("type", "text");
    dateFieldInput.setAttribute("value", date);
    dateFieldInput.id = "date" + id;


    let deleteBtn = document.createElement("a");
    deleteBtn.setAttribute("data-role", "button");
    let contentDeleteBtn = document.createTextNode("Delete");
    deleteBtn.appendChild(contentDeleteBtn);

    deleteBtn.setAttribute("href", "#add");

    deleteBtn.addEventListener("click", function () {
        deleteItem(id);
    });

//headers for Items
    let yourItemNameHeader = document.createElement("h3");
    let contentYourItemNameHeader = document.createTextNode("Item Name:");
    yourItemNameHeader.appendChild(contentYourItemNameHeader);

    let categoryHeader = document.createElement("h3");
    let contentCategoryHeader = document.createTextNode("Category:");
    categoryHeader.appendChild(contentCategoryHeader);

    let costHeader = document.createElement("h3");
    let contentCostHeader = document.createTextNode("Cost:");
    costHeader.appendChild(contentCostHeader);

    let dateHeader = document.createElement("h3");
    let contentDateHeader = document.createTextNode("Date:");
    dateHeader.appendChild(contentDateHeader);


    wrapper.appendChild(yourItemNameHeader);
    wrapper.appendChild(nameFieldInput);

    wrapper.appendChild(categoryHeader);
    wrapper.appendChild(categoryFieldInput);

    wrapper.appendChild(costHeader);
    wrapper.appendChild(costFieldInput);

    wrapper.appendChild(dateHeader);
    wrapper.appendChild(dateFieldInput);

    wrapper.appendChild(deleteBtn);

    newItem.appendChild(itemTitle);
    newItem.appendChild(wrapper);

    return newItem;
}

/**
 * This function calls the viewModel to get
 * all items for the View Report page
 */
function getReportItems(){window.viewModel.getReport();}

/**
 * This function gets all the list of items ,and according to
 * the selected choice of report by months from the UI.
 * The selected items presented on the graph in View Report page
 * @param result Contains all user items from database
 */
function displayReport(result) {

    var yearSelected = parseInt(document.getElementById('year').value);
    var fromMonthSelected = parseInt(document.getElementById('fromdate').value);
    var toMonthSelected = parseInt(document.getElementById('todate').value);

    let jsonObject = JSON.parse(result);
    let myItems = jsonObject.myitems;
    var counterArray = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];

    for (let i = 0; i < myItems.length; i++) {

        var myItemCategory = myItems[i].ITEM_CATEGORY;
        var myItemCost = parseInt(myItems[i].ITEM_COST);
        var myItemDate = myItems[i].ITEM_DATE;

        var res = myItemDate.split("-");
        var monthDB = parseInt(res[1]);
        var yearDB = parseInt(res[0]);

        if (monthDB >= fromMonthSelected && monthDB <= toMonthSelected && yearDB == yearSelected) {
            if (myItemCategory == "Food")
                counterArray[0] = counterArray[0] + myItemCost;
            else if (myItemCategory == "Furnitures")
                counterArray[1] = counterArray[1] + myItemCost;
            else if (myItemCategory == "Home Tax")
                counterArray[2] = counterArray[2] + myItemCost;
            else if (myItemCategory == "Mortgage")
                counterArray[3] = counterArray[3] + myItemCost;
            else if (myItemCategory == "Fuel")
                counterArray[4] = counterArray[4] + myItemCost;
            else if (myItemCategory == "Car Fix")
                counterArray[5] = counterArray[5] + myItemCost;
            else if (myItemCategory == "Water")
                counterArray[6] = counterArray[6] + myItemCost;
            else if (myItemCategory == "Electric")
                counterArray[7] = counterArray[7] + myItemCost;
            else if (myItemCategory == "Phone")
                counterArray[8] = counterArray[8] + myItemCost;
            else if (myItemCategory == "Internet")
                counterArray[9] = counterArray[9] + myItemCost;
            else if (myItemCategory == "Gas")
                counterArray[10] = counterArray[10] + myItemCost;
            else if (myItemCategory == "Closes")
                counterArray[11] = counterArray[11] + myItemCost;
            else if (myItemCategory == "Etc")
                counterArray[12] = counterArray[12] + myItemCost;
        }
    }
    /**
    * Creates the graph on the UI
    */
    am4core.ready(function () {

// Themes begin
        am4core.useTheme(am4themes_animated);
// Themes end

        var chart = am4core.create("chartdiv", am4charts.SlicedChart);
        chart.hiddenState.properties.opacity = 0; // this makes initial fade in effect

        chart.data = [{
            "name": "Food",
            "value": counterArray[0]
        }, {
            "name": "Furnitures",
            "value": counterArray[1]
        }, {
            "name": "Home Tax",
            "value": counterArray[2]
        }, {
            "name": "Mortgage",
            "value": counterArray[3]
        }, {
            "name": "Fuel",
            "value": counterArray[4]
        }, {
            "name": "Car Fix",
            "value": counterArray[5]
        }, {
            "name": "Water",
            "value": counterArray[6]
        }, {
            "name": "Electric",
            "value": counterArray[7]
        }, {
            "name": "Phone",
            "value": counterArray[8]
        }, {
            "name": "Internet",
            "value": counterArray[9]
        }, {
            "name": "Gas",
            "value": counterArray[10]
        }, {
            "name": "Closes",
            "value": counterArray[11]
        }, {
            "name": "Etc",
            "value": counterArray[12]
        }];

        var series = chart.series.push(new am4charts.FunnelSeries());
        series.colors.step = 2;
        series.dataFields.value = "value";
        series.dataFields.category = "name";
        series.alignLabels = true;

        series.labelsContainer.paddingLeft = -5;
        series.labelsContainer.width = 100;

    }); // end am4core.ready()
}



