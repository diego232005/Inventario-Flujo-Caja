export default function Alert({ type = 'info', children, onRetry }) {
  return (
    <div className={`alert ${type}`} role="alert">
      <div>{children}</div>
      {onRetry && (
        <button type="button" className="btn" style={{ marginTop: 8 }} onClick={onRetry}>
          Reintentar
        </button>
      )}
    </div>
  )
}
