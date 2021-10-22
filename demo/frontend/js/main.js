const apiEndPoint = "http://localhost:5000/autocomplete";

async function autokomplete(query) {
  const body = { query };
  try {
    const { data: suggestions } = await axios.post(apiEndPoint, body);
    return suggestions.suggestions;
  } catch (error) {
    return [];
  }
}

function activate_autokomplete(inputElement) {
  let currentFocus;

  inputElement.addEventListener("input", async function (e) {
    closeAllLists();
    if (!inputElement.value) return;

    currentFocus = -1;
    const suggestionList = document.createElement("DIV");
    suggestionList.setAttribute("id", inputElement.id + "autocomplete-list");
    suggestionList.setAttribute("class", "autocomplete-items");
    inputElement.parentNode.appendChild(suggestionList);

    const suggestions = await autokomplete(inputElement.value);

    for (let i = 0; i < suggestions.length; i++) {
      const suggestion = document.createElement("DIV");
      suggestion.innerHTML = suggestions[i];
      suggestion.innerHTML +=
        "<input type='hidden' value='" + suggestions[i] + "'>";
      suggestion.addEventListener("click", function (e) {
        inputElement.value = suggestion.getElementsByTagName("input")[0].value;
        closeAllLists();
      });
      suggestionList.appendChild(suggestion);
    }
  });

  inputElement.addEventListener("keydown", function (e) {
    let suggestionList = document.getElementById(
      inputElement.id + "autocomplete-list"
    );
    if (suggestionList)
      suggestionList = suggestionList.getElementsByTagName("div");
    if (e.keyCode == 40) {
      currentFocus++;
      addActive(suggestionList);
    } else if (e.keyCode == 38) {
      currentFocus--;
      addActive(suggestionList);
    } else if (e.keyCode == 13) {
      e.preventDefault();
      if (currentFocus > -1 && suggestionList) {
        suggestionList[currentFocus].click();
      }
    }
  });

  function addActive(suggestionList) {
    if (!suggestionList) return;
    removeActive(suggestionList);
    if (currentFocus >= suggestionList.length) currentFocus = 0;
    if (currentFocus < 0) currentFocus = suggestionList.length - 1;
    suggestionList[currentFocus].classList.add("autocomplete-active");
  }

  function removeActive(suggestionList) {
    for (var i = 0; i < suggestionList.length; i++) {
      suggestionList[i].classList.remove("autocomplete-active");
    }
  }

  function closeAllLists(element) {
    var suggestionList = document.getElementsByClassName("autocomplete-items");
    for (var i = 0; i < suggestionList.length; i++) {
      if (element != suggestionList[i] && element != inputElement) {
        suggestionList[i].parentNode.removeChild(suggestionList[i]);
      }
    }
  }

  document.addEventListener("click", function (e) {
    closeAllLists(e.target);
  });
}

activate_autokomplete(document.getElementById("query"));
