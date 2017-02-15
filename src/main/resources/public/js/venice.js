function addPropertyListEntry(dl, name, value) {
    var nameElement = $(document.createElement("DT"));
    nameElement.text(name);
    dl.append(nameElement);

    var valueElement = $(document.createElement("DD"));
    valueElement.text(value);
    dl.append(valueElement);
}

function createEnvironmentContainer(env) {
    var container = $(document.createElement("DIV"));

    var title = $(document.createElement("H2"));
    title.text(env.name);
    container.append(title);

    var row = $(document.createElement("DIV"));
    row.addClass("row");

    Object.keys(env.servers).forEach(function (serverName, i) {
        var server = env.servers[serverName];

        var serverElement = $(document.createElement("DIV"));
        serverElement.addClass("col-sm-3");
        if (i > 0) {
            serverElement.addClass("col-sm-offset-1");
        }
        serverElement.addClass("server");
        if (server.status == "OK") {
            serverElement.addClass("server-ok");
        } else if (server.status == "WARNING") {
            serverElement.addClass("server-warning");
        } else if (server.status == "Unknown") {
            serverElement.addClass("server-unknown");
        } else {
            serverElement.addClass("server-nok");
        }

        var serverNameElement = $(document.createElement("H3"));
        serverNameElement.text(serverName);
        serverElement.append(serverNameElement);

        var propertyList = $(document.createElement("DL"));
        addPropertyListEntry(propertyList, "Version", server.version);
        serverElement.append(propertyList);

        row.append(serverElement);
    });

    container.append(row);

    return container;
}

function createApplicationContainer(app) {
    var container = $(document.createElement("DIV"));

    var title = $(document.createElement("H1"));
    title.text(app.name);
    container.append(title);

    app.environments.forEach(function (env) {
        container.append(createEnvironmentContainer(env));
    });

    return container;
}

$(window).load(function () {
    $.getJSON("/probes", function (applications) {
        var appContainer = $("#app-container");
        applications.forEach(function (app) {
            appContainer.append(createApplicationContainer(app));
        });
    });
});
