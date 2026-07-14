import { useProfileStore } from "../../store/profileStore";
import { useUiStore } from "../../store/uiStore";
import Icon from "../ui/Icon";
import ProfileMenu from "./ProfileMenu";

export default function TopBar({ title }) {
  const profile = useProfileStore((s) => s.profile);
  const theme = useUiStore((s) => s.theme);
  const toggleTheme = useUiStore((s) => s.toggleTheme);

  return (
    <header className="sticky top-0 z-20 flex items-center justify-between gap-3 border-b-2 border-[var(--border)] bg-[var(--surface)] px-4 py-3 md:px-6">
      <h2 className="min-w-0 truncate text-xl font-extrabold tracking-tight">{title}</h2>
      <div className="flex shrink-0 items-center gap-2 text-xs font-bold">
        <button
          onClick={toggleTheme}
          aria-label={`Switch to ${theme === "dark" ? "light" : "dark"} mode`}
          className="flex items-center justify-center rounded-full bg-[var(--surface-2)] p-2 transition-colors hover:bg-[var(--border)]"
        >
          <Icon name={theme === "dark" ? "light_mode" : "dark_mode"} size={16} className="text-[var(--accent)]" />
        </button>
        <span className="flex items-center gap-1 rounded-full bg-[var(--surface-2)] px-3 py-1.5">
          <Icon name="local_fire_department" size={16} fill={1} className="text-[var(--accent)]" />
          {profile?.streakDays ?? 0}
        </span>
        <ProfileMenu />
      </div>
    </header>
  );
}
