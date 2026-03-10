import threading
import time

class Bank:
    balance = 0
    lock = threading.Lock()

    def deposit(self):
        time.sleep(1)  # Simulates delay
        Bank.balance += 100

    def withdraw(self):
        Bank.balance -= 100

    def get_value(self):
        return Bank.balance

    def run(self):
        with Bank.lock:
            self.deposit()
            print(f"Value for Thread after deposit {threading.current_thread().name} {self.get_value()}")
            self.withdraw()
            print(f"Value for Thread after withdraw {threading.current_thread().name} {self.get_value()}")
