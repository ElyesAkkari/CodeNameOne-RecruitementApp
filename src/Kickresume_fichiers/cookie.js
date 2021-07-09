function getCookie(cname) {
  const name = `${cname}=`;
  const ca = document.cookie.split(";");
  for (let i = 0; i < ca.length; i += 1) {
    let c = ca[i];
    while (c.charAt(0) == " ") {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function setCookie(name, value, days) {
  let expires = "";
  if (days) {
    const date = new Date();
    date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
    expires = `; expires=${date.toUTCString()}`;
  }

  const cookie = typeof value === "object" ? JSON.stringify(value) : "";

  document.cookie = `${name}=${cookie || ""}${expires}; path=/`;
}

function saveConsentCookies(value) {
  setCookie("cookie_consent", value, 365);
  if (window.ENABLE_GOOGLE_TAG) {
    const eventName = value.advertising ? "marketingCookiesEnabled" : "marketingCookiesDisabled";
    window.dataLayer.push({ event: eventName });
  }
  window.location.reload();
}

function openCookieTab(tabName, button) {
  const tabcontent = document.querySelectorAll(".cookies .modal-body .cookie-modal-main");
  for (let i = 0; i < tabcontent.length; i += 1) {
    tabcontent[i].classList.remove("open");
  }

  const tablinks = document.querySelectorAll(".cookies .modal-body .cookie-modal-sidebar .item");
  for (let i = 0; i < tablinks.length; i += 1) {
    tablinks[i].classList.remove("selected");
  }

  button.classList.add("selected");

  document.getElementById(tabName).classList.add("open");
}

function changeCookieState(name, isActive) {
  const panelElm = document.getElementById("cookie-panel");
  const stateElm = document.getElementById(name).querySelector(".heading-state");
  if (stateElm) {
    stateElm.innerHTML = isActive ? panelElm.dataset.activeStateLabel : panelElm.dataset.inactiveStateLabel;
    stateElm.classList.remove("active", "inactive");
    stateElm.classList.add(isActive ? "active" : "inactive");
  }
}

function handleCheckboxChange(name, checkbox) {
  changeCookieState(name, checkbox.checked);
}

document.addEventListener("DOMContentLoaded", () => {
  const modalElm = document.getElementById("cookies-settings-modal");
  const modalContentElm = modalElm.querySelector(".cookies .modal-content");
  const modalCloseElm = modalElm.querySelector(".cookies .close");

  const performanceCheckboxElm = document.getElementById("performance-cookie-setting");
  const functionalCheckboxElm = document.getElementById("functional-cookie-setting");
  const advertisingCheckboxElm = document.getElementById("advertising-cookie-setting");

  let outerClick = true;
  let isModalClosable = false;

  const cookie_consent = getCookie("cookie_consent");

  if (!cookie_consent) {
    const banner = document.getElementById("cookie-consent-banner");
    if (banner) {
      banner.style.display = "flex";
    }
    changeCookieState("Performance", false);
    changeCookieState("Functional", false);
    changeCookieState("Advertising", false);
  } else {
    const cookieSettings = JSON.parse(decodeURIComponent(cookie_consent));
    performanceCheckboxElm.checked = cookieSettings.performance;
    functionalCheckboxElm.checked = cookieSettings.functional;
    advertisingCheckboxElm.checked = cookieSettings.advertising;
    changeCookieState("Performance", cookieSettings.performance);
    changeCookieState("Functional", cookieSettings.functional);
    changeCookieState("Advertising", cookieSettings.advertising);
  }

  const openStyle = isClosable => {
    modalElm.style.backgroundColor = "rgba(0,0,0,0.5)";
    modalElm.style.display = "block";
    modalCloseElm.style.display = isClosable ? "block" : "none";
    isModalClosable = isClosable;
    setTimeout(() => {
      modalElm.style.opacity = 1;
    });
  };

  const closeStyle = () => {
    modalElm.style.display = "none";
    modalElm.style.opacity = 0;
  };

  // NO CLOSE MODAL WHEN YOU CLICK INSIDE MODAL
  modalContentElm.onclick = () => {
    outerClick = false;
  };

  // CLOSE MODAL WHEN YOU CLICK OUTSIDE MODAL
  modalElm.onclick = () => {
    if (outerClick && isModalClosable) {
      closeStyle();
    }
    outerClick = true;
  };

  modalCloseElm.onclick = () => {
    closeStyle();
  };

  document.body.addEventListener("click", event => {
    if (event.target.id === "cookie_settings_btn") {
      openStyle(false);
    }
    if (event.target.id === "footer-nav-cookie-settings") {
      openStyle(true);
    }
    if (event.target.id === "accept_all_btn") {
      saveConsentCookies({
        performance: true,
        functional: true,
        advertising: true,
      });
    }
    if (event.target.id === "save_settings_btn") {
      saveConsentCookies({
        performance: performanceCheckboxElm.checked,
        functional: functionalCheckboxElm.checked,
        advertising: advertisingCheckboxElm.checked,
      });
    }
  });
});
