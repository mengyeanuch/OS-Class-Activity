import threading
import time

# shared buffer
buffer = []

# semaphores
empty = threading.Semaphore(100)
full = threading.Semaphore(0)
mutex = threading.Semaphore(1)

def producer(id):
    while True:  # PLoop
        P1 = f"P{id}-1"
        P2 = f"P{id}-2"

        print(f"Producer {id} produced pair")

        empty.acquire()
        empty.acquire()

        mutex.acquire()

        buffer.append(P1)
        buffer.append(P2)
        print(f"Producer {id} placed {P1} {P2} in buffer")

        mutex.release()

        full.release()
        full.release()

        time.sleep(1)


def consumer():
    while True:  # CLoop
        full.acquire()
        full.acquire()

        mutex.acquire()

        P1 = buffer.pop(0)
        P2 = buffer.pop(0)

        mutex.release()

        empty.release()
        empty.release()

        print(f"Consumer packaged {P1} {P2}")

        time.sleep(2)


# multiple producers
producers = []
for i in range(3):
    t = threading.Thread(target=producer, args=(i,))
    producers.append(t)

# single consumer
consumer_thread = threading.Thread(target=consumer)

# start threads
for p in producers:
    p.start()

consumer_thread.start()