import { useState } from 'react';

export default function RegisterPage() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [location, setLocation] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        // On Day 2, we will call our API here.
        console.log({ name, email, password, location }); 
    };

    return (
        <div>
            <h2>Register for GoviCare</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} required />
                <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                <input type="text" placeholder="Location (e.g., Malabe)" value={location} onChange={(e) => setLocation(e.target.value)} required />
                <button type="submit">Register</button>
            </form>
        </div>
    );
}