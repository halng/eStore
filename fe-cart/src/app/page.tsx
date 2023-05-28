import Image from 'next/image'
import styles from './page.module.css'
import Cart from '../components/cart';

export default function Home() {
  return (
    <main className={styles.main}>
      <div className={styles.description}>
        <Cart />
      </div>
    </main>
  )
}
