import styles from './page.module.css';
import Welcome from '@/components/welcome';

export default function Home() {
  return (
    <main className={styles.main}>
      <div className={styles.description}>
        <Welcome></Welcome>
      </div>
    </main>
  );
}
