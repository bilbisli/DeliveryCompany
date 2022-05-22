# DeliveryCompany

This project displays a concurrent delivery system simulation implemented using Design Patterns.
* Docs are available in the doc folder (can be viewed by ```index.html```).

### Program Features:
<ol>
  <li>System Creation Panel:</li>
  <ul>
    <li>Number of Post Office Branches.</li>
    <li>Number of Trucks per Post Office Branch.</li>
    <li>Number of Packages to create throughout the simulation.</li>
  </ul>
  <li>Start - starts the simulation.</li>
  <li>Stop - pauses the simulation.</li>
  <li>Resume - resumes the simulation.</li>
  <li>All Packages Info - displays a table of informing the current state of all the created packages.</li>
  <li>Branch Info - package information of a specific branch.</li>
  <li>CloneBranch - deep copies a selected branch.</li>
  <li>Resume - resumes the simulation.</li>
  <li>Restore - restores the simulation to the initial settings and runs it.</li>
  <li>Report - creates a text log file logging the trace of all the packages created and opens it to be viewed.</li>
</ol>

### Design Patterns used:
<ol>
  <li>Thread-safe Singleton + DCL - the MainOffice is a singleton secured by Double-Checked Locking (DCL - another pattern).</li>
  <li>Prototype & Clone - the Branches are a prototype that are cloned when CloneBranch is operated.</li>
  <li>Memento - restoring the simulation uses the memento pattern which stores the initial state and reloads it upon request.</li>
  <li>Thread Pool - the Customers are managed with a thread pool</li>
  <li>Observer-Listener - MainOffice is an observer and Branches, Customers & Trucks are observables.</li>
  <li>Read/Write Lock - reading & writing to the report file is managed by a read/write lock.</li>
  <li>Producer-Consumer - the packages are produced and 'consumed' (dispatched) based on this pattern.</li>
</ol>

### Program Simulation Demo:
[![Delivery Company Simulation Video](https://user-images.githubusercontent.com/73055024/169673318-997d0ae4-6e61-4782-8a60-a8930d5e6cb6.mov)](https://user-images.githubusercontent.com/73055024/169673318-997d0ae4-6e61-4782-8a60-a8930d5e6cb6.mov)
