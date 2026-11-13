export default function StatusBadge({ critico, antiguo }) {
  if (critico) return <span className="badge crit">Crítico</span>
  if (antiguo) return <span className="badge old">&gt;90 días</span>
  return <span className="badge ok">OK</span>
}
