const DataLookup = () => {
  const fetchData = () => {
    var apiUrl = "http://localhost:8001/viewData";
    const outputElement = document.getElementById("LookupResults");

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
    apiUrl += document.getElementById("lookup-query").value;
    outputElement.textContent = apiUrl;
    //Execute URL
    fetch(apiUrl, {
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
        outputElement.textContent = JSON.stringify(data, null, 2);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
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
