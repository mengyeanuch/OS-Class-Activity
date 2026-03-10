from bank import Bank
import threading

def main():
    bank_instance = Bank()
    # while True:
    t1 = threading.Thread(target=bank_instance.run, name="Thread1")
    t2 = threading.Thread(target=bank_instance.run, name="Thread2")
    t3 = threading.Thread(target=bank_instance.run, name="Thread3")

    t1.start()
    t2.start()
    t3.start()

    t1.join()
    t2.join()
    t3.join()

if __name__ == "__main__":
    main()
