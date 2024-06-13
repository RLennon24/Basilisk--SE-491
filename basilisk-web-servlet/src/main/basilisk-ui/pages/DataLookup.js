const DataLookup = () => {
  const fetchData = () => {
    var apiUrl = "http://localhost:8001/viewData";
    const outputElement = document.getElementById("LookupResults");
    outputElement.textContent = "";
    //TODO Set up contingency in case of occupied port

    //Build Lookup URL
    var lookupType = document.getElementById("lookup-type").value;
    if (lookupType == "ID Lookup") {
      apiUrl += "/idStr/";
    } else if (lookupType == "Role Lookup") {
      apiUrl += "/role/";
    } else {
      apiUrl += "/tag/";
    }

    //Load input(s) into array for URL to grab piecemeal in future for-loop
    let lookupInput = [];
    let lookupRaw = document.getElementById("lookup-query").value;
    lookupRaw = lookupRaw.replace(/\s/g, '');
    let commaIndex = lookupRaw.length;
    while (commaIndex >= 0 ){
      lookupInput.push(lookupRaw.substring(0,commaIndex));
      lookupRaw = lookupRaw.substring(commaIndex);
      commaIndex = lookupRaw.indexOf(",");
    }
    //Go through the array
    for (let i = 0; i < lookupInput.length; i++){
      var thisApiUrl = apiUrl + lookupInput[i];
      //Execute URL
      fetch(thisApiUrl, {
      method: "GET", // *GET, POST, PUT, DELETE, etc.
      mode: "cors", // no-cors, *cors, same-origin
      cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
      credentials: "same-origin", // include, *same-origin, omit
      headers: {
        "Content-Type": "text/plain",
        "DATA-OWNER": "Marcel",
      },
      redirect: "follow", // manual, *follow, error
      referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        //Check for length of data received; return null message if none found
        var entries = Object.keys(data).length;
        if (entries == 0){
          outputElement.textContent = "No entries found";
        }
        //Parse returned data from API into readable strings
        else{
          const outputResults = JSON.stringify(data,null,1);
          const parsedResults = JSON.parse(outputResults);
          var trueResults = "" + parsedResults.length + " entries found:\n\n";
          var itemHolder;
          var itemParsed;
          //Load each entry of returned data and parse + print them individually
          //to be displayed in UI
          for (let i = 0; i < parsedResults.length; i++){
            itemHolder = JSON.stringify(parsedResults[i],null,2);
            itemParsed = JSON.parse(itemHolder);
            trueResults += "ID: " + itemParsed.id;
            trueResults += "\nData: " + itemParsed.data;
            trueResults += "\nTags: " + itemParsed.tags;
            trueResults += "\nRoles: " + itemParsed.roles;
            trueResults += "\n\n";
          }
          outputElement.textContent += trueResults;
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
    }    
  };

  return (
    <div className="holder">
      <section id="data-lookup">
        <h2>Data Lookup</h2>
        Search by:
        <select id="lookup-type">
          <option value="ID Lookup">ID</option>
          <option value="Role Lookup">Role</option>
          <option value="Tag Lookup">Tag</option>
        </select>
        <input type="text" id="lookup-query" />
        <input type="button" value="Search" onClick={() => fetchData()} />
        <div><pre id="LookupResults"></pre></div>
      </section>
    </div>
  );
};

export default DataLookup;
