import { create } from "zustand";

function getInitialTheme() {
  const saved = localStorage.getItem("theme");
  if (saved === "dark" || saved === "light") return saved;
  return window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
}

function applyTheme(theme) {
  if (theme === "dark") {
    document.documentElement.classList.add("dark");
  } else {
    document.documentElement.classList.remove("dark");
  }
  localStorage.setItem("theme", theme);
}

export const useUiStore = create((set) => ({
  sidebarOpen: true,
  activeQuizSessionId: sessionStorage.getItem("activeQuizSessionId"),
  theme: getInitialTheme(),
  setSidebarOpen: (open) => set({ sidebarOpen: open }),
  setActiveQuizSession: (id) => {
    sessionStorage.setItem("activeQuizSessionId", id);
    set({ activeQuizSessionId: id });
  },
  clearQuizSession: () => {
    sessionStorage.removeItem("activeQuizSessionId");
    set({ activeQuizSessionId: null });
  },
  toggleTheme: () => {
    const next = useUiStore.getState().theme === "dark" ? "light" : "dark";
    applyTheme(next);
    set({ theme: next });
  },
  setTheme: (theme) => {
    applyTheme(theme);
    set({ theme });
  },
}));

// Apply theme on load
applyTheme(useUiStore.getState().theme);
